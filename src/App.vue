<template>
  <div id="app">
    <router-view/>

    <audio id="newOrder" controls="controls" hidden="true" muted>
      <source src="static/audio/newOrder.mp3"    type="audio/mpeg" />
    </audio>

  </div>
</template>

<script>
 import {getWebSockNewOrder} from '@/api/websock'
  export default {
    name: 'App',
    created() {
      //webSock连接
      this.initWebSock();
    },
    destroyed() {
      this.webSockOnClose()
    },
    data(){
      return {
        ws: '',
        keepAliveFlag: true,
        clock: -1,
        audio: '',
        interval: ''

      }
    },
    methods: {
      initWebSock(){
        //此处代码加载时自动循环调用,用户交互就能成功调用播放-


          //const wsUrl = 'ws://localhost:8099' + '/webSocket/addOrder/' + 'store1';
          const wsUrl = 'ws://47.97.127.106:9300' + '/webSocket/addOrder/' + 'store1';
          console.log("开始建立连接...")
          this.ws = new WebSocket(wsUrl); //建立连接
          this.ws.onopen = this.webSockOnOpen;//连接成功后触发的函数
          this.ws.onmessage = this.webSockOnmessage;//数据接收后触发的函数
          this.ws.onclose = this.webSockOnClose();//关闭时触发
          this.ws.onclose = this.webSockOnError();//出错时触发

      },//连接成功后触发的函数
      webSockOnOpen(){
        console.log('WebSocket连接打开！');
        this.getNewOrder();

        //this.keepAliveCheck();
      },//数据接收后触发的函数
      webSockOnmessage(e){
        if (e.data == 'pong'){
          this.keepAliveFlag = true;
          console.log('webSocket连接正常');
        }else {
          console.log('WebSocket收到消息！'+e.data);
          //消息接收成功的操作函数
          this.onSocketMessageSucc(e.data);
        }
      },//关闭时触发
      webSockOnClose(){
        console.log('WebSocket连接关闭');
        // clearInterval(this.clock);
        // this.ws.close();
      },//出错时触发
      webSockOnError(){
        console.log('WebSocket连接错误');
        // clearInterval(this.clock);
        // this.ws.close();
      },//打开连接5s后，获取redis中未消费的数据
      getNewOrder(){
        setTimeout(function (){
          getWebSockNewOrder().then(res => {
            if (res.code == 200){
              console.log("getNewOrder: "+JSON.stringify(res));
            }else {
              this.$message(res.message)
            }
          })
        },5000)
      },//心跳检测机制(每15s发送一个请求'ping' 要是在10s内接收到'pong'就代表心跳正常，否则重新连接)
      keepAliveCheck: function () {
        let _this = this;
        _this.clock = setInterval(function () {

          console.log('webSocket心跳检测');
          _this.ws.send("ping");
          _this.keepAliveFlag = false;

          setTimeout(function (){
            console.log("test10")
            if (!_this.keepAliveFlag) {
              //关闭
              _this.webSockOnClose();
              console.log('webSocket准备重连');
              //重连
              _this.initWebSock();
            }
          },10000)

        }, 15000);
      },//接收的消息时操作函数
      onSocketMessageSucc(e){
        //window.document.getElementById("newOrder").pause();

        window.document.getElementById("newOrder").play();

        this.$notify({
          title: '提示',
          type: 'warning',
          message: e,
          duration: 0
        });
      }

 }
  }
</script>

<style>
</style>
