from threading import Thread
import json
from urllib.request import urlopen
import time

from multiprocessing import Process, cpu_count
import os

cities=['Edmonton','Victoria','Winnipeg','Fredericton','St. John\'s','Halifax','Toronto'
    ,'Charlottetown','Quebec City','Regina']

class TempGetter(Thread):
    def __init__(self, city):
        super().__init__()
        self.city=city

    def run(self):
        url_template=('http://api.openwethermap.org/data/2.5/weather?q={},CA&units=metric')
        response=urlopen(url_template.format(self.city))
        data=json.loads(response.read().decode())
        self.temperature=data['main']['temp']

def thread_example():
    threads = [TempGetter(c) for c in cities]
    start = time.time()
    for thread in threads:
        thread.start()

    for thread in threads:
        thread.join()

    for thread in threads:
        print("it is {0.temperature:.0f}° C in {0.city}".format(thread))

    print("Got {} temps in {} seconds".format(len(threads), time.time()-start))

class MuchCpu(Process):
    # 如果将Process 换成Thread，速度会明显降低，
    # 因为全局解释器锁会影响线程执行，但是进程不会受影响
    def run(self):
        print(os.getpid())
        for i in range(2000000):
            pass

def multithread_example():
    procs = [MuchCpu() for f in range(cpu_count())]
    t = time.time()
    for p in procs:
        p.start()
    for p in procs:
        p.join()
    print('work took {} seconds'.format(time.time()-t))

if __name__ == "__main__":
    multithread_example()