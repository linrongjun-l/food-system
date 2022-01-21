<template>
  <div class="app-container">
    <!-- <div class="address-layout">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">后台项目</div>
            <div class="color-main address-content">
              <a href="https://github.com/macrozheng/mall">mall</a>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">前端项目</div>
            <div class="color-main address-content">
              <a href="https://github.com/macrozheng/mall-admin-web">mall-admin-web</a>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">学习教程</div>
            <div class="color-main address-content">
              <a href="https://github.com/macrozheng/mall-learning">mall-learning</a>
            </div>
          </div>
        </el-col>
      </el-row>
    </div> -->
    <div class="total-layout">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="total-frame">
            <img :src="img_home_order" class="total-icon">
            <div class="total-title">今日订单总数</div>
            <div class="total-value">{{oneDayTotalOrder}}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="total-frame">
            <img :src="img_home_today_amount" class="total-icon">
            <div class="total-title">今日销售总额</div>
            <div class="total-value">￥{{oneDayTotalSale}}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="total-frame">
            <img :src="img_home_yesterday_amount" class="total-icon">
            <div class="total-title">昨日销售总额</div>
            <div class="total-value">￥{{yesterdayTotalSale}}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="total-frame">
            <svg-icon icon-class="total-week" class="total-icon">
            </svg-icon>
            <div class="total-title">近7天销售总额</div>
            <div class="total-value">￥{{sevenDayTotalSale   }}</div>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- <el-card class="mine-layout">
      <div style="text-align: center">
        <img width="150px" height="150px" src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/banner/qrcode_for_macrozheng_258.jpg">
      </div>
      <div style="text-align: center">mall全套学习教程连载中！</div>
      <div style="text-align: center;margin-top: 5px"><span class="color-main">关注公号</span>，第一时间获取。</div>
    </el-card> -->
    <div class="un-handle-layout">
      <div class="layout-title">待处理事务</div>
      <div class="un-handle-content">
        <el-row :gutter="20">
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">待付款订单</span>
              <span style="float: right" class="color-danger">({{order0}})</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">已完成订单</span>
              <span style="float: right" class="color-danger">({{order3}})</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">待确认收货订单</span>
              <span style="float: right" class="color-danger">({{order2}})</span>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">待发货订单</span>
              <span style="float: right" class="color-danger">({{order1}})</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">新缺货登记</span>
              <span style="float: right" class="color-danger">(10)</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">待处理退款申请</span>
              <span style="float: right" class="color-danger">({{order7}})</span>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">已发货订单</span>
              <span style="float: right" class="color-danger">({{order2}})</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">待处理退货订单</span>
              <span style="float: right" class="color-danger">({{order7}})</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">广告位即将到期</span>
              <span style="float: right" class="color-danger">(10)</span>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <div class="overview-layout">
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="out-border">
            <div class="layout-title">商品总览</div>
            <div style="padding: 40px">
              <el-row>
                <el-col :span="6" class="color-danger overview-item-value">100</el-col>
                <el-col :span="6" class="color-danger overview-item-value">400</el-col>
                <el-col :span="6" class="color-danger overview-item-value">50</el-col>
                <el-col :span="6" class="color-danger overview-item-value">500</el-col>
              </el-row>
              <el-row class="font-medium">
                <el-col :span="6" class="overview-item-title">已下架</el-col>
                <el-col :span="6" class="overview-item-title">已上架</el-col>
                <el-col :span="6" class="overview-item-title">库存紧张</el-col>
                <el-col :span="6" class="overview-item-title">全部商品</el-col>
              </el-row>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="out-border">
            <div class="layout-title">用户总览</div>
            <div style="padding: 40px">
              <el-row>
                <el-col :span="6" class="color-danger overview-item-value">100</el-col>
                <el-col :span="6" class="color-danger overview-item-value">200</el-col>
                <el-col :span="6" class="color-danger overview-item-value">1000</el-col>
                <el-col :span="6" class="color-danger overview-item-value">5000</el-col>
              </el-row>
              <el-row class="font-medium">
                <el-col :span="6" class="overview-item-title">今日新增</el-col>
                <el-col :span="6" class="overview-item-title">昨日新增</el-col>
                <el-col :span="6" class="overview-item-title">本月新增</el-col>
                <el-col :span="6" class="overview-item-title">会员总数</el-col>
              </el-row>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="statistics-layout">
      <div class="layout-title">订单统计</div>
      <el-row>
        <el-col :span="4">
          <div style="padding: 20px">
            <div>
              <div style="color: #909399;font-size: 14px">本月订单总数</div>
              <div style="color: #606266;font-size: 24px;padding: 10px 0">{{curMonthTotalOrder}}</div>
              <div>
                <span class="color-success" style="font-size: 14px">{{comparePrevMonthTotalOrder}}</span>
                <span style="color: #C0C4CC;font-size: 14px">同比上月</span>
              </div>
            </div>
            <div style="margin-top: 20px;">
              <div style="color: #909399;font-size: 14px">本周订单总数</div>
              <div style="color: #606266;font-size: 24px;padding: 10px 0">{{curWeekTotalOrder}}</div>
              <div>
                <span class="color-danger" style="font-size: 14px">{{comparePrevWeekTotalOrder}}</span>
                <span style="color: #C0C4CC;font-size: 14px">同比上周</span>
              </div>
            </div>
            <div style="margin-top: 20px;">
              <div style="color: #909399;font-size: 14px">本月销售总额</div>
              <div style="color: #606266;font-size: 24px;padding: 10px 0">{{curMonthTotalSale}}</div>
              <div>
                <span class="color-success" style="font-size: 14px">{{comparePrevMonthTotalSale}}</span>
                <span style="color: #C0C4CC;font-size: 14px">同比上月</span>
              </div>
            </div>
            <div style="margin-top: 20px;">
              <div style="color: #909399;font-size: 14px">本周销售总额</div>
              <div style="color: #606266;font-size: 24px;padding: 10px 0">{{curWeekTotalSale}}</div>
              <div>
                <span class="color-danger" style="font-size: 14px">{{comparePrevWeekTotalSale}}</span>
                <span style="color: #C0C4CC;font-size: 14px">同比上周</span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="20">
          <div style="padding: 10px;border-left:1px solid #DCDFE6">
            <el-date-picker
              style="float: right;z-index: 1"
              size="small"
              v-model="orderCountDate"
              type="daterange"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="handleDateChange"
              :picker-options="pickerOptions">
            </el-date-picker>
            <div>
              <ve-line
                :data="chartData"
                :legend-visible="false"
                :loading="loading"
                :data-empty="dataEmpty"
                :settings="chartSettings"></ve-line>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import {str2Date} from '@/utils/date';
  import img_home_order from '@/assets/images/home_order.png';
  import img_home_today_amount from '@/assets/images/home_today_amount.png';
  import img_home_yesterday_amount from '@/assets/images/home_yesterday_amount.png';
  import {formatDate} from '@/utils/date';
  import {getOrderCount,totalOrderSale,getOrderStatus,getMonthOrder,getWeekOrder} from '@/api/orderStatistics'
  let DATA_FROM_BACKEND = {
    columns: ['date', 'orderCount','orderAmount'],
    rows: [
      {date: '2018-11-01', orderCount: 10, orderAmount: 1093},
      {date: '2018-11-02', orderCount: 20, orderAmount: 2230},
      {date: '2018-11-03', orderCount: 33, orderAmount: 3623},
      {date: '2018-11-04', orderCount: 50, orderAmount: 6423},
      {date: '2018-11-05', orderCount: 80, orderAmount: 8492},
      {date: '2018-11-06', orderCount: 60, orderAmount: 6293},
      {date: '2018-11-07', orderCount: 20, orderAmount: 2293},
      {date: '2018-11-08', orderCount: 60, orderAmount: 6293},
      {date: '2018-11-09', orderCount: 50, orderAmount: 5293},
      {date: '2018-11-10', orderCount: 30, orderAmount: 3293},
      {date: '2018-11-11', orderCount: 20, orderAmount: 2293},
      {date: '2018-11-12', orderCount: 80, orderAmount: 8293},
      {date: '2018-11-13', orderCount: 100, orderAmount: 10293},
      {date: '2018-11-14', orderCount: 10, orderAmount: 1293},
      {date: '2018-11-15', orderCount: 40, orderAmount: 4293}
    ]
  };
  export default {
    name: 'home',
    data() {
      return {
        pickerOptions: {
          disabledDate(e){
            //console.log(e)
            return e.getTime() > new Date().getTime();
          },
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              let start = new Date();
              let end = new Date();
              start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一月',
            onClick(picker) {
              let start = new Date();
              let end = new Date();
              start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        orderCountDate: '',
        chartSettings: {
          xAxisType: 'time',
          area:true,
          axisSite: { right: ['orderAmount']},
        labelMap: {'orderCount': '订单数量', 'orderAmount': '订单金额'}},
        chartData: {
          columns: [],
          rows: []
        },
        oneMonthChartData: [],
        loading: false,
        dataEmpty: false,
        img_home_order,
        img_home_today_amount,
        img_home_yesterday_amount,
        oneDayTotalSale: 0,
        oneDayTotalOrder: 0,
        yesterdayTotalSale: 0,
        sevenDayTotalSale: 0,
        order0: 0, //待付款
        order1: 0,//带发货
        order2: 0,//已发货
        order3: 0,//已完成
        order4: 0,//已关闭
        order5: 0,//已取消
        order6: 0,//退货申请中
        order7: 0,// 退货成功
        curMonthTotalSale: 0,//这个月销售总额
        curMonthTotalOrder: 0,//这个月订单总数
        prevMonthTotalSale: 0,//上个月销售总额
        prevMonthTotalOrder: 0,//上个月订单总数
        curWeekTotalSale: 0,//这周销售总额
        curWeekTotalOrder: 0,//这周订单总数
        prevWeekTotalSale: 0,//上周销售总额
        prevWeekTotalOrder: 0//上周订单总数
      }
    },
    created: function () {
      //获取这一个月（从xxxx-xx-01 --- xxxx-xx-today）订单数据
      this.getOneMonth().then(res => {
        console.log("322getOneMonth"+res)
        this.oneMonthChartData = res.data;
        //ECharts图日期初始化
        this.initOrderCountDate();
        //ECharts数据初始化
        this.handleDateChange();
        //今日订单总数
        this.oneDayTotalOrder = this.oneDayTotalOrders();
        //今日订单总额
        this.oneDayTotalSale = this.oneDayTotalSales();
        //昨日销售总额
        this.yesterdayTotalSale = this.yesterdayTotalSales();
        //最近七天销售总额
        this.sevenDayTotalSales();
        //订单状态
        this.getOrderStatuss()
        this.getMonthOrders();
        this.getWeekOrders();
      });

    },
    methods:{
       getOneMonth(){
        let newDay = formatDate(new Date(),"yyyy-MM-dd");
        let firstDay = newDay.substring(0,8) + "01";
         return getOrderCount(firstDay,newDay);
      },
      handleDateChange(){

        let beginTime = formatDate(this.orderCountDate[0], 'yyyy-MM-dd')
        let endTime = formatDate(this.orderCountDate[1], 'yyyy-MM-dd')
        this.getData(beginTime,endTime)

      },
      initOrderCountDate(){
        let start = new Date();
        let end = new Date();
        start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 7);
        this.orderCountDate=[start,end];
      },
      getData(beginTime,endTime){

        let len = this.oneMonthChartData.length;

        if (str2Date(beginTime).getTime() >= str2Date(this.oneMonthChartData[len - 1].date).getTime()
          && str2Date(endTime).getTime() <= str2Date(this.oneMonthChartData[0].date).getTime()){

          this.chartData = {
            columns: ['date', 'orderCount','orderAmount'],
            rows: []
          };
          for(let i=0;i<len;i++){
            let item=this.oneMonthChartData[i];
            let currDate=str2Date(item.date);
            let start=this.orderCountDate[0];
            let end=this.orderCountDate[1];
            if(currDate.getTime()>=start.getTime()&&currDate.getTime()<=end.getTime()){
              this.chartData.rows.push(item);
            }
          }
        }else {
          getOrderCount(beginTime,endTime).then(res =>{
            if (res.code == 200){

              let result = res.data;

              this.chartData = {
                columns: ['date', 'orderCount','orderAmount'],
                rows: []
              };
              for(let i=0;i<result.length;i++){
                let item=result[i];
                let currDate=str2Date(item.date);
                let start=this.orderCountDate[0];
                let end=this.orderCountDate[1];
                if(currDate.getTime()>=start.getTime()&&currDate.getTime()<=end.getTime()){
                  this.chartData.rows.push(item);
                }
              }
              this.dataEmpty = false;
              this.loading = false
            }else {
              this.$message(res.message);
            }
          })
        }

      },
      //今天销售总额
      oneDayTotalSales(){
        return this.oneMonthChartData[0].orderAmount;
      },
      //今天订单总数
      oneDayTotalOrders(){
        console.log(this.oneMonthChartData[0].date)
        return this.oneMonthChartData[0].orderCount;
      },
      //昨日销售总额
      yesterdayTotalSales(){
        return this.oneMonthChartData[1].orderAmount;
      },
      //七天销售总额
      sevenDayTotalSales(){
         totalOrderSale(7).then(res => {
           this.sevenDayTotalSale = res.data.orderAmount;
         });
      },
      getOrderStatuss(){
        getOrderStatus().then(res => {
          let orderStatus = res.data;
          for (let item of orderStatus){
            switch (item.status){
              case 0:
                this.order0 = item.count;
                break;
              case 1:
                this.order1 = item.count;
                break;
              case 2:
                this.order2 = item.count;
                break;
              case 3:
                this.order3 = item.count;
                break;
              case 4:
                this.order4 = item.count;
                break;
              case 5:
                this.order5 = item.count;
                break;
              case 6:
                this.order6 = item.count;
                break;
              case 7:
                this.order7 = item.count;
                break;
            }

          }
        })
      },
      getMonthOrders(){
        getMonthOrder().then(res => {
          if (res.code == 200){
            this.curMonthTotalOrder = res.data[0].orderCount;
            this.curMonthTotalSale = res.data[0].orderAmount;
            this.prevMonthTotalOrder = res.data[1].orderCount;
            this.prevMonthTotalSale = res.data[1].orderAmount;
          }else {
            this.$message(res.message)
          }
        });

      },
      getWeekOrders(){
        getWeekOrder().then(res => {
          if (res.code == 200){
            this.curWeekTotalOrder = res.data[0].orderCount;
            this.curWeekTotalSale = res.data[0].orderAmount;
            this.prevWeekTotalOrder = res.data[1].orderCount;
            this.prevWeekTotalSale = res.data[1].orderAmount;
          }else {
            this.$message(res.message)
          }
        });
      },
    },
    computed: {
      //同比上月订单总数
      comparePrevMonthTotalOrder(){
        let res = ((parseFloat(this.curMonthTotalOrder) - parseFloat(this.prevMonthTotalOrder))/parseFloat(this.curMonthTotalOrder))
        res = Math.round(res * 100);
        return res >= 0 ? "+"+res+"%": res+"%";
      },
      //同比上月订单总额
      comparePrevMonthTotalSale(){
        let res = ((parseFloat(this.curMonthTotalSale) - parseFloat(this.prevMonthTotalSale))/parseFloat(this.curMonthTotalSale))
        res = Math.round(res * 100);
        return res >= 0 ? "+"+res+"%": res+"%";
      },
      //同比上周订单总数
      comparePrevWeekTotalOrder(){
        let res = ((parseFloat(this.curWeekTotalOrder) - parseFloat(this.prevWeekTotalOrder))/parseFloat(this.curWeekTotalOrder));
        res = Math.round(res * 100);
        return res >= 0 ? "+"+res+"%": res+"%";
      },
      //同比上周订单总额
      comparePrevWeekTotalSale(){
        let res = ((parseFloat(this.curWeekTotalSale) - parseFloat(this.prevWeekTotalSale))/parseFloat(this.curWeekTotalSale));
        res = Math.round(res * 100);
        return res >= 0 ? "+"+res+"%": res+"%";
      },
    }
  }
</script>

<style scoped>
  .app-container {
    margin-top: 40px;
    margin-left: 120px;
    margin-right: 120px;
  }

  .address-layout {
  }

  .total-layout {
    margin-top: 20px;
  }

  .total-frame {
    border: 1px solid #DCDFE6;
    padding: 20px;
    height: 100px;
  }

  .total-icon {
    color: #409EFF;
    width: 60px;
    height: 60px;
  }

  .total-title {
    position: relative;
    font-size: 16px;
    color: #909399;
    left: 70px;
    top: -50px;
  }

  .total-value {
    position: relative;
    font-size: 18px;
    color: #606266;
    left: 70px;
    top: -40px;
  }

  .un-handle-layout {
    margin-top: 20px;
    border: 1px solid #DCDFE6;
  }

  .layout-title {
    color: #606266;
    padding: 15px 20px;
    background: #F2F6FC;
    font-weight: bold;
  }

  .un-handle-content {
    padding: 20px 40px;
  }

  .un-handle-item {
    border-bottom: 1px solid #EBEEF5;
    padding: 10px;
  }

  .overview-layout {
    margin-top: 20px;
  }

  .overview-item-value {
    font-size: 24px;
    text-align: center;
  }

  .overview-item-title {
    margin-top: 10px;
    text-align: center;
  }

  .out-border {
    border: 1px solid #DCDFE6;
  }

  .statistics-layout {
    margin-top: 20px;
    border: 1px solid #DCDFE6;
  }
  .mine-layout {
    position: absolute;
    right: 140px;
    top: 107px;
    width: 250px;
    height: 235px;
  }
  .address-content{
    padding: 20px;
    font-size: 18px
  }
</style>
