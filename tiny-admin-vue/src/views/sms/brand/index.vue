<script setup lang="ts">

import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {getBannerPage} from "@/api/banner.ts";
import {useDebounceFn} from "@vueuse/core";

const handleAdd = () => {
  bannerModalRef.value.showModal()
}

const switchLoading = ref(false)
const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0,
})
const searchForm = ref({
  keyword: '',
  status: true,
  pageNum: pagination.value.current,
  pageSize: pagination.value.pageSize,
})
const dataSource = ref([])

const queryList = () => {
  switchLoading.value = true
  searchForm.value.keyword = searchForm.value.keyword.trim()
  getBannerPage(searchForm.value).then((res:any) => {
    dataSource.value = res.records
  }).finally(() => {
    switchLoading.value = false
  })
}
queryList()
const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)
const queryByStatus = () => {

}

const columns:any = [{
  title: '序号',
  dataIndex: 'index',
  key: 'index',
  width: 80,
  align: 'center',
}, {
  title: '图片',
  dataIndex: 'picUrl',
  key: 'picUrl',
  width: 100,
  align: 'center',
}, {
  title: '品牌名称',
  dataIndex: 'bannerName',
  key: 'bannerName',
  width: 200,
  align: 'center',
}, {
  title: '平台',
  dataIndex: 'platform',
  key: 'platform',
  width: 100,
  align: 'center',
},{
  title: '备注',
  dataIndex: 'remark',
  key: 'remark',
  width: 160,
  align: 'center',
}, {
  title: '排序',
  dataIndex:'sort',
  key:'sort',
  width: 100,
  align: 'center',
}, {
  title: '操作',
  key: 'operation',
  width: 160,
  fixed: 'right',
}]

const handleTableChange = (pagination, filters, sorter) => {

}
const bannerModalRef = ref()
const handleEdit = (record) => {
  bannerModalRef.value.showModal(record)
}
const handleDelete = (id) => {

}
</script>

<template>
  <div>
    <div class="p-4">
      <div class="flex mb-4">
        <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
          <a-button type="primary" @click="handleAdd">新增</a-button>
          <a-input id="keyword" v-model:value="searchForm.keyword" allow-clear
                   autocomplete="off" class="text-left"
                   placeholder="搜索品牌名、简介、品牌故事"
                   type="text" @keyup.enter.native="queryList">
            <template #prefix>
              <a-switch v-model:checked="searchForm.status" :loading="switchLoading" checked-children="已启用"
                        class="flex-shrink-0" un-checked-children="已禁用" @change="queryByStatus"/>
            </template>
            <template #suffix>
              <a-button type="primary" @click="queryList">搜索</a-button>
            </template>
          </a-input>
        </div>
      </div>
    </div>

    <a-table :columns="columns" :dataSource="dataSource"
             :pagination="pagination"
             :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
             @change="handleTableChange">
      <template #bodyCell="{record, index, column}">
        <template v-if="column.key === 'index'">
          {{ index + 1 }}
        </template>
        <template v-else-if="column.dataIndex === 'picUrl'">
          <img :src="record.picUrl"/>
        </template>
        <template v-else-if="column.dataIndex === 'bannerName'">
          <a-tooltip :arrow="false">
            <template #title>
              <span>{{ record.bannerName }}</span>
            </template>
            <span>{{ record.bannerName?.substring(0, 20) }}</span>
            <span v-if="record.bannerName?.length > 20">...</span>
          </a-tooltip>
        </template>
        <template v-else-if="column.dataIndex === 'platform'">
          {{record.platform === 1 ? '电脑端' : '移动端'}}
        </template>
        <template v-else-if="column.dataIndex === 'remark'">
          <a-tooltip :arrow="false">
            <template #title>
              <span>{{ record.remark }}</span>
            </template>
            <span>{{ record.remark?.substring(0, 10) }}</span>
            <span v-if="record.remark?.length > 10">...</span>
          </a-tooltip>
        </template>
        <template v-else-if="column.dataIndex === 'sort'">
          {{ record.sort }}
        </template>

        <template v-else-if="column.key === 'operation'">
          <div class="grid grid-cols-2 items-center justify-center">
            <a-popconfirm :title="record.status ? '是否禁用该品牌？' : ' 是否启用该品牌？'"
                          cancel-text="否" ok-text="是"
                          @confirm="() => {confirmChangeStatus(record)}">
              <template #icon>
                <question-circle-outlined style="color: red"/>
              </template>
              <a-switch v-model:checked="record.status" :loading="record.loading" checked-children="已启用"
                        class="flex-shrink-0" :checked-value="1" :un-checked-value="0"
                        size="small" un-checked-children="已禁用" @click="() => {changeStatus(record)}"/>
            </a-popconfirm>
            <a-dropdown placement="bottom" trigger="hover">
              <a-button class="flex items-center" size="small" type="link">
                操作
                <DownOutlined/>
              </a-button>
              <template #overlay>
                <a-menu class="text-center">
                  <a-menu-item>
                    <a-button type="link" @click="() => handleEdit(record)">编辑轮播卡片</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm cancel-text="否" ok-text="是"
                                  ok-type="danger"
                                  @confirm="() => {deleteBrand(record.id)}">
                      <template #icon>
                        <question-circle-outlined style="color: red"/>
                      </template>
                      <template #title>
                        <div>是否删除轮播卡片？</div>
                        <a-tag class="my-2" color="red">{{ record.brandName }}</a-tag>
                      </template>
                      <a-button danger type="link">删除轮播卡片</a-button>
                    </a-popconfirm>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
        </template>
      </template>
    </a-table>

  </div>
</template>

<style scoped>

</style>