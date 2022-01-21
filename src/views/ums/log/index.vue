<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
          style="float:right"
          type="primary"
          @click="handleSearchList()"
          size="small">
          查询搜索
        </el-button>
        <el-button
          style="float:right;margin-right: 15px"
          @click="handleResetSearch()"
          size="small">
          重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input v-model="listQuery.userName" class="input-width" placeholder="帐号/姓名" clearable></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
<!--    <el-card class="operate-container" shadow="never">-->
<!--      <i class="el-icon-tickets"></i>-->
<!--      <span>数据列表</span>-->
<!--      <el-button size="mini" class="btn-add" @click="handleAdd()" style="margin-left: 20px">添加</el-button>-->
<!--    </el-card>-->
    <div class="table-container">
      <el-table ref="adminTable"
                :data="list"
                style="width: 100%;"
                v-loading="listLoading" border>
        <el-table-column label="编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="账号" align="center">
          <template slot-scope="scope">{{scope.row.userName}}</template>
        </el-table-column>
        <el-table-column label="IP" align="center">
          <template slot-scope="scope">{{scope.row.ip}}</template>
        </el-table-column>
        <el-table-column label="登入时间" align="center">
          <template slot-scope="scope">{{scope.row.createTime | formatDateTime}}</template>
        </el-table-column>
        <el-table-column label="登入地址" width="160" align="center">
          <template slot-scope="scope">{{scope.row.address }}</template>
        </el-table-column>
        <el-table-column label="浏览器类型" width="160" align="center">
          <template slot-scope="scope">{{scope.row.userAgent}}</template>
        </el-table-column>

      </el-table>
    </div>
    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes,prev, pager, next,jumper"
        :current-page.sync="listQuery.pageNum"
        :page-size="listQuery.pageSize"
        :page-sizes="[10,15,20]"
        :total="total">
      </el-pagination>
    </div>

  </div>
</template>
<script>
import {fetchListLog} from '@/api/login';
import {formatDate} from '@/utils/date';

const defaultListQuery = {
  pageNum: 1,
  pageSize: 10,
  userName: null
};
// const defaultAdmin = {
//   id: null,
//   userName: null,
//   createTime: null,
//   ip: null,
//   address: null,
//   userAgent: null,
// };
export default {
  name: 'adminList',
  data() {
    return {
      listQuery: Object.assign({}, defaultListQuery),
      list: null,
      total: null,
      listLoading: false,
      //admin: Object.assign({}, defaultAdmin),
    }
  },
  created() {
    this.getList();

  },
  filters: {
    formatDateTime(time) {
      if (time == null || time === '') {
        return 'N/A';
      }
      let date = new Date(time);
      return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
    }
  },
  methods: {
    /**
     * 重置
     */
    handleResetSearch() {
      this.listQuery = Object.assign({}, defaultListQuery);
    },
    /**
     * 搜索
     */
    handleSearchList() {
      this.listQuery.pageNum = 1;
      this.getList();
    },
    /**
     * 分页
     * @param val
     */
    handleSizeChange(val) {
      this.listQuery.pageNum = 1;
      this.listQuery.pageSize = val;
      this.getList();
    },
    /**
     * 分页
     * @param val
     */
    handleCurrentChange(val) {
      this.listQuery.pageNum = val;
      this.getList();
    },

    getList() {
      this.listLoading = true;
      fetchListLog(this.listQuery).then(response => {
        this.listLoading = false;
        this.list = response.data.list;
        this.total = response.data.total;
      });
    },

  }
}
</script>
<style></style>



