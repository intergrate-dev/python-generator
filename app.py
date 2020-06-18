import os
import time
import tarfile
import traceback
import json
import random
import math
from flask import Flask, render_template, send_from_directory, request
from build import config, discern_type, connectDB

s = config.read_yaml()
db = connectDB.connect_db()
app = Flask(__name__)
cursor = db.cursor()
table_list = connectDB.get_table_list_map()
pre_package = s['pre_package']
base_page = s['base_page']
title = s['title']
path = s['path']


@app.route('/index')
def index():
    return render_template('create_class.html', **table_list)


@app.route("/download/<filename>", methods=['GET'])
def downloader(filename):
    # 指定文件下载目录，默认为当前项目根路径
    dirpath = os.path.join(app.root_path, '')
    # as_attachment=True 表示下载
    return send_from_directory(dirpath, filename, as_attachment=True)


@app.route('/createClass', methods=['GET', 'POST'])
def create_class():
    file_name = msg = None
    # {'column': {'age': 'int', 'id': 'String', 'address': 'String', 'name': 'String'}, 'table': 'cc_user'}
    table = request.form['table']
    if len(table) <= 0:
        msg = 'request data json is null!'
    result = get_column(table)
    column = result[0]

    package = pre_package + table  # 包名
    package = package.replace('_', '')
    table_name = table
    table = table.title().replace('_', '')

    if len(table) <= 0:
        msg = 'className is null!'
    if len(package) <= 0:
        msg = 'package is null'
    print(table + '\n' + package)
    if not msg or len(msg) <= 0:
        d = time.strftime("%Y-%m-%d", time.localtime())
        entity = request.form.get('entity')
        if entity and len(entity) >= 1:
            print('--- create entity class')
            create_entity(table, package, column, d)
        Vo = request.form.get('Api')
        if Vo and len(Vo) >= 1:
            create_Api(table, package, d, result)
            print('--- create Api doc')
        Vo = request.form.get('Vo')
        if Vo and len(Vo) >= 1:
            create_Vo(table, package, d, result)
            print('--- create Vo class')
        DTO = request.form.get('DTO')
        if DTO and len(DTO) >= 1:
            create_DTO(table, package, column, d)
            create_page(table, package, d)
            print('--- create DTO class')
        dao = request.form.get('dao')
        if dao and len(dao) >= 1:
            print('--- create dao class')
            create_dao(table, package, d)
            create_xml(table, table_name, package, result)
        service = request.form.get('service')
        if service and len(service) >= 1:
            print('--- create service class')
            create_service(table, package, d)
            create_service_impl(table, package, d, result)
        controller = request.form.get('controller')
        ds = request.form.get('Do')
        if ds and len(ds) >= 1:
            print('--- create do class')
            create_do(table, package, d, result)
        if controller and len(controller) >= 1:
            print('--- create controller class')
            create_controller(table, package, d)
        file_name = make_targz()
    return render_template('create_class.html', msg=msg, file_name=file_name, **table_list)

# 创建entity
def create_entity(class_name, package, columns, date):
    propertys = ''
    if columns:
        for key in columns.keys():
            propertys += '/** \n *' + columns[key][1] + ' \n */ \n'
            propertys += 'private %s %s;' % (columns[key][0], key) + '\n\n'
    c = {'package': package + '.entity',
         'entity_package': package + '.entity.' + class_name,
         'class_name': class_name,
         'propertys': propertys,
         'date': date}
    s = render_template('entity_templates.html', **c)
    create_java_file(class_name, package + '.entity', s)

# 创建entity
def create_DTO(class_name, package, columns, date):
    propertys = ''
    if columns:
        for key in columns.keys():
            propertys += '/** \n *' + columns[key][1] + ' \n */ \n'
            if columns[key][0] == 'String':
                propertys += '@NotBlank(message = "' + key + '不能为空\")\n'
            else:
                propertys += '@NotNull(message = "' + key + '不能为空\")\n'
            propertys += 'private %s %s;' % (columns[key][0], key) + '\n\n'
    c = {'package': package + '.dto',
         'entity_package': package + '.dto.' + class_name,
         'class_name': class_name + 'DTO',
         'propertys': propertys,
         'date': date
         }
    s = render_template('entity_templates.html', **c)
    create_java_file(class_name + 'DTO', package + '.dto', s)


def create_page(class_name, package, date):
    c = {'package': package + '.dto',
         'class_name': class_name + 'PageDTO',
         'dto_name': class_name + 'DTO',
         'dto_package': package + '.dto.' + class_name + 'DTO',
         'date': date}
    s = render_template('page_templates.html', **c)
    create_java_file(class_name + 'PageDTO', package + '.dto', s)


# 创建Dao
def create_dao(class_name, package, date):
    c = {'package': package + '.dao',
         'class_name': class_name,
         'small_class_name': small_str(class_name),
         'entity_package': package + '.entity.' + class_name,
         'date': date,
         'vo_package': package + '.vo.' + class_name + 'Vo',
         'dto_package': package + '.dto.' + class_name + 'DTO'
         }
    s = render_template('dao_templates.html', **c)
    create_java_file(class_name + 'Dao', package + '.dao', s)


def create_xml(class_name, table_name, package, result):
    all_column = ''
    insert_column = ''
    for index, item in enumerate(result[1]):
        if index == 0:
            insert_column += '\n            ' + item + ',\n'
            all_column += '\n            ' + item + ',\n'
        elif index == 1:
            insert_column += '            ' + item + ',\n'
            all_column += '            ' + item + ',\n'
        else:
            insert_column += '            ' + item + ',\n'
            all_column += '            ' + item + ',\n'

    insert_column = insert_column[:-2] + '\n'
    all_column = all_column[:-2] + '\n'
    entity_id = result[1][0]
    result[0].pop(change_str(entity_id))

    # insert = insert_value(result[0])
    insert = insert_value(result)
    c = {'package': package + '.dao',
         'name_space': 'portal.' + class_name,
         'vo_entity': package + '.vo.' + class_name + 'Vo',
         'do_entity': package + '.dos.' + class_name + 'Do',
         'class_name': class_name,
         'columns': result[0],
         'id': result[1][0],
         'entity_id': entity_id,
         'column_': all_column,
         'table_name': table_name,
         'insert_column': insert_column,
         'insert_value': insert[0],
         'batch_insert_value': insert[1]
         }
    s = render_template('entity_mysql_mapper_templates.html', **c)
    create_java_file(class_name + 'Dao', package + '.dao', s, '.xml')


def insert_value(columns):
    insert_value = ''
    batch_insert_value = ''
    # for index, item in enumerate(columns.keys()):
    for index, item in enumerate(columns[1]):
        if index == 0:
            insert_value += '\n            #{' + item + '},\n'
            batch_insert_value += '\n            #{item.' + item + '},\n'
        else:
            insert_value += '            #{' + item + '},\n'
            batch_insert_value += '            #{item.' + item + '},\n'
    insert = (insert_value[:-2] + '\n', batch_insert_value[:-2] + '\n')
    return insert


# 创建Service
def create_service(class_name, package, date):
    c = {'service_package': package + '.service',
         'class_name': class_name,
         'small_class_name': small_str(class_name),
         'entity_package': package + '.entity.' + class_name,
         'dao_package': package + '.dos.' + class_name + 'Do',
         'date': date,
         'vo_entity': package + '.vo.' + class_name + 'Vo'
         }
    # s = render_template('service_templates.html', **c)
    s = render_template('service.txt', **c)
    create_java_file(class_name + 'Service', package + '.service', s)


# 创建Service
def create_service_impl(class_name, package, date, result):
    c = {'package': package + '.service.impl',
         'class_name': class_name,
         'small_class_name': small_str(class_name),
         'date': date,
         'id': result[1][0],
         'vo_entity': package + '.vo.' + class_name + 'Vo',
         'do_entity': package + '.dos.' + class_name + 'Do',
         'service_entity': package + '.service.' + class_name + 'Service'
         }
    # s = render_template('service_templates_impl.html', **c)
    s = render_template('service_impl.txt', **c)
    create_java_file(class_name + 'ServiceImpl', package + '.service.impl', s)

# 创建Do
def create_do(class_name, package, date, result):
    c = {'package': package + '.dos',
         'class_name': class_name,
         'small_class_name': small_str(class_name),
         'entity_package': package + '.entity.' + class_name,
         'do_package': package + '.dos.' + class_name + 'Do',
         'service_package': package + '.service.' + class_name + 'Service',
         'date': date,
         'id': result[1][0],
         'columns': result[0],
         'vo_entity': package + '.vo.' + class_name + 'Vo'
         }
    # s = render_template('do_templates_impl.html', **c)
    s = render_template('do.txt', **c)
    print("create dos ...")
    create_java_file(class_name + 'Do', package + '.dos', s)

# 创建Api
def create_Api(class_name, package, date, result):
    host = "localhost"
    # raw_json = {"txnCommCom":{"txnIttChnlId":"1","txnIttChlCgyCode":"1"},"txnBodyCom":{"f1":"123","f2":"wrt"}}
    small_class = small_str(class_name)
    json_body = {small_class: entity_json(result, True)}
    raw_json = {"txnCommCom":{"txnIttChnlId":"1","txnIttChlCgyCode":"1"},"txnBodyCom":json_body}
    raw_str = json_to_str(raw_json)
    c = {'package': package + '.Api',
         'class_name': class_name,
         'small_class_name': small_class,
         'entity_package': package + '.entity.' + class_name,
         'vo_package': package + '.vo.' + class_name + 'Vo',
         'service_package': package + '.service.' + class_name + 'Service',
         'date': date,
         'host': host,
         'host_array': list_to_str(host.split('.')),
         'port': '8080',
         'body_raw': raw_str,
         'id': result[1][0],
         'columns': result[0]
         }
    # s = render_template('do_templates_impl.html', **c)
    service_registry = render_template('service_registry.txt', **c)
    api_postman = render_template('rest_api_postman.txt', **c)
    print("create service_registry, api of postman...")
    create_java_file('service_registry', package + '.Api', service_registry, '.txt')
    create_java_file('Api-doc-test.postman_clollection', package + '.Api', api_postman, '.json')

# 创建Vo
def create_Vo(class_name, package, date, result):
    c = {'package': package + '.vo',
         'class_name': class_name,
         'small_class_name': small_str(class_name),
         'entity_package': package + '.entity.' + class_name,
         'vo_package': package + '.vo.' + class_name + 'Vo',
         'service_package': package + '.service.' + class_name + 'Service',
         'date': date,
         'id': result[1][0],
         'columns': result[0]
         }
    # s = render_template('do_templates_impl.html', **c)
    s = render_template('vo.txt', **c)
    print("create dos ...")
    create_java_file(class_name + 'Vo', package + '.vo', s)

# 创建controller
def create_controller(class_name, package, date):
    c = {'package': package + '.controller',
         'project': pre_package,
         'title': title,
         'class_name': class_name,
         'small_class_name': small_str(class_name),
         'entity_package': package + '.entity.' + class_name,
         'service_package': package + '.service.' + class_name + 'Service',
         'date': date,
         'vo_package': package + '.vo.' + class_name + 'Vo',
         'dto_package': package + '.dto.' + class_name + 'DTO',
         'page_entity': package + '.dto.' + class_name + 'PageDTO',
         'base_page': base_page}
    s = render_template('controller_templates.html', **c)
    # print(s)
    create_java_file(class_name + 'Controller', package + '.controller', s)


# 将首字母转换为小写
def small_str(s):
    if len(s) <= 1:
        return s
    return (s[0:1]).lower() + s[1:]


# 创建java文件
def create_java_file(class_name, package, text, suffix='.java'):
    dirs = path + package.replace('.', '/') + '/'
    if not os.path.exists(dirs):
        os.makedirs(dirs, 0o777)
    fd = os.open(dirs + class_name + suffix, os.O_WRONLY | os.O_CREAT)
    os.write(fd, text.encode(encoding="utf-8", errors="strict"))
    os.close(fd)


def make_targz():
    file_name = 'com.tar.gz'
    source_dir = path
    with tarfile.open(file_name, "w:gz") as tar:
        tar.add(source_dir, arcname=os.path.basename(source_dir))
    return file_name


def get_column(table_name):
    columns = {}  # 列
    columns_ = []
    sql = """SELECT
                COLUMN_NAME,
                DATA_TYPE,
                COLUMN_COMMENT 
            FROM
                information_schema.COLUMNS 
            WHERE
                TABLE_SCHEMA = """ + "'" + connectDB.date_name + "' " + """AND TABLE_NAME = """ + "'" + table_name + "'"
    try:
        # 执行sql语句获取队列类型及备注
        cursor.execute(sql)
        results = cursor.fetchall()
        for row in results:
            tuple = (discern_type.discern_type(row[1]), row[2], row[0])
            columns[change_str(row[0])] = tuple
            columns_.append(row[0])
        result = (columns, columns_)
        return result
        # create_entity(table_name, package, columus, date)
    except Exception:
        print('traceback.format_exc():\n%s' % traceback.format_exc())


def change_str(column):
    str_list = str(column).split("_")
    first = str_list[0].lower()
    others = str_list[1:]

    others_capital = [word.capitalize() for word in others]  # str.capitalize():将字符串的首字母转化为大写
    others_capital[0:0] = [first]

    hump_string = ''.join(others_capital)
    return hump_string

def list_to_str(list):
    a = '['
    for w in list:
        a += '"' + w + '", '
    return a[:-2] + ']'

def json_to_str(d_json):
    raw_str = json.dumps(d_json)
    return '"' + raw_str.replace('"', '\\"') + '"'

def entity_json(result, is_create):
    e_json = {}
    y = 'zyxwvutsrqponmlkjihgfedcba'
    if(is_create):
        for index, item in enumerate(result[1]):
            if index == 0:
                r = math.floor(1e6 * random.random())
                e_json[item] = '%d' %r
            else:
                e_json[item] = ''.join(random.sample(y, random.randint(4, 10)))
    else:
        for index, item in enumerate(result[1]):
            e_json[item] = ''.join(random.sample(y, random.randint(4, 10)))
    print(e_json)
    return e_json

if __name__ == '__main__':
    app.run()
