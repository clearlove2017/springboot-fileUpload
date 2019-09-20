# springboot-fileUpload
spring cloud微服务项目中一个专门负责处理文件上传下载业务的服务，支持单个文件或多个文件同时上传到私有的文件服务器，阿里云的oss（不同环境需要不同配置）。另外支持excel下载，采用阿里easyexcel，在大数据量的业务下性能优化很大。有其他问题或建议欢迎提出来，如果这个项目能帮到你，希望能给我点个赞，非常感谢！

需要添加的配置如下：
spring.profiles.active=
#ftp相关配置,对应自己环境的文件服务器地址
FTP.BASEPATH=/home/ftpuser
#图片服务器相关配置
IMAGE.BASE.URL=http://192.168.*.*

# ftp连接池配置
ftp.client.host=192.168.*.*
ftp.client.port=21
ftp.client.username=yourusername
ftp.client.password=yourpassword
ftp.client.encoding=utf-8
ftp.client.passiveMode=false
ftp.client.connectTimeout=60000
ftp.client.dataTimeout=60000
ftp.client.keepAliveTimeout=30
# aliyun oss配置
aliyun.oss.endpoint=
aliyun.oss.accessKeyId=
aliyun.oss.accessKeySecret=
