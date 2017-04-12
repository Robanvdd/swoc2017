import subprocess
x = "D:\\SWOC\\swoc2017\\MicroVis\\build\\exe\\microVis\\MicroVis.exe"
subprocess.call([x, '{ "positions": [ "2,3", "3,4" ] }'])