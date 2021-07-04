import tornado.ioloop
import tornado.web
from switch import *

if __name__ == "__main__":
    application = tornado.web.Application([
        (r"/(.*)", Switch),
    ])
    application.listen(8888) 
    tornado.ioloop.IOLoop.instance().start()



