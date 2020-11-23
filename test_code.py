import json
import time
import random
import math

str='heLLoWord'

new_str='{}{}'.format(str[0].upper(),str[1:])
# print(new_str)


# print("title: " + str.title())

host_1 = "localhost"
host_2 = "127.2.2.8"
list_1 = host_1.split('.')
list_2 = host_2.split('.')
# print(list_1)
# print(list_2)
a = '['
for w in list_2:
    a += '"' + w + '", '
a = a[:-2] + ']'
# print(a)

list_3 = '["1", "3", "0"]'
# print(list_3)

raw = {"txnCommCom":{"txnIttChnlId":"1","txnIttChlCgyCode":"1"},"txnBodyCom":{"f1":"123","f2":"wrt"}}
str = json.dumps(raw)
str = '"' + str.replace('"', '\\"') + '"'
print(str)


arr = ['a','b']
str = ''.join(arr)
print(str)
ss = round(time.time() * 1000)
print(ss)

nums = math.floor(1e6 * random.random())

print(nums)

tt=322
tem='%d' %nums
print('|' + tem + '|')


hh = "excange_metadata_id"
hs = hh.split('_')
yy = hs[0] + hs[1].capitalize() + hs[2].capitalize()
print(yy)

for index in range(0, len(hs)):
    print(hs[index])