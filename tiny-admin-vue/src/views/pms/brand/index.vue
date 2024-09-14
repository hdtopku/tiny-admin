<script lang="ts" setup>
import {getBrandPage} from "@/api/brand.ts";
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {useDebounceFn} from "@vueuse/core";

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
const switchLoading = ref(false)
const saveOrUpdateBrand = () => {

}
const queryList = () => {
  switchLoading.value = true
  getBrandPage(searchForm.value).then((res: any) => {
    dataSource.value = res.records
    pagination.value = {
      current: res.current,
      pageSize: res.size,
      total: res.total,
    }
  }).finally(() => {
    switchLoading.value = false
  })
}
queryList()
const queryByStatus = () => {
  switchLoading.value = true
}


const columns = [
  {
    title: '序号',
    key: 'index',
    width: 60,
  },
  {
    title: '品牌logo',
    dataIndex: 'logo',
    key: 'logo',
    width: 100,
  },
  {
    title: '品牌名',
    dataIndex: 'brandName',
    key: 'brandName',
  },
  {
    title: '品牌简介',
    dataIndex: 'brandDesc',
    key: 'brandDesc',
    width: 200,
  },
  {
    title: '品牌故事',
    dataIndex: 'brandStory',
    key: 'brandStory',
    width: 200,
  },
  {
    title: '操作',
    key: 'operation',
    fixed: 'right',
    width: 160,
  }
]
const dataSource = ref([])
const handleTableChange = (pagination: any, filters: any, sorter: any) => {
  searchForm.value.pageNum = pagination.current
  searchForm.value.pageSize = pagination.pageSize
  queryList()
}

const deleteBrand = (id: any) => {

}


const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)
const changeStatus = (record: any) => {
  record.status = !record.status
}
</script>

<template>
  <div>
    <div class="p-4">
      <div class="flex mb-4">
        <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
          <a-button type="primary" @click="saveOrUpdateBrand">新增</a-button>
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
      <template #bodyCell="{record, index,column}">
        <template v-if="column.key === 'index'">
          {{ index + 1 }}
        </template>
        <template v-else-if="column.dataIndex === 'logo'">
          <a-avatar :src="record.logo" shape="square" size="large"/>
        </template>
        <template v-else-if="column.dataIndex === 'brandDesc'">
          <a-tooltip :arrow="false">
            <template #title>
              <span>{{ record.brandDesc }}</span>
            </template>
            <span>{{ record.brandDesc.substring(0, 10) }}</span>
            <span v-if="record.brandDesc.length > 10">...</span>
          </a-tooltip>
        </template>
        <template v-else-if="column.dataIndex === 'brandStory'">
          <a-tooltip :arrow="false">
            <template #title>
              <span>{{ record.brandStory }}</span>
            </template>
            <span>{{ record.brandStory.substring(0, 10) }}</span>
            <span v-if="record.brandStory.length > 10">...</span>
          </a-tooltip>
        </template>
        <template v-else-if="column.key === 'operation'">
          <div class="grid grid-cols-2 items-center justify-center">
            <a-popconfirm :title="record.status ? '是否禁用该用户？' : ' 是否启用该用户？'"
                          cancel-text="否" ok-text="是"
                          @confirm="() => {confirmChangeStatus(record)}">
              <template #icon>
                <question-circle-outlined style="color: red"/>
              </template>
              <a-switch v-model:checked="record.status" :loading="record.loading" checked-children="已启用"
                        class="flex-shrink-0"
                        size="small" un-checked-children="已禁用" @click="() => {changeStatus(record)}"/>
            </a-popconfirm>
            <a-dropdown placement="bottomCenter" trigger="hover">
              <a-button class="flex items-center" size="small" type="link">
                操作
                <DownOutlined/>
              </a-button>
              <template #overlay>
                <a-menu class="text-center">
                  <a-menu-item>
                    <a-button type="link" @click="() => saveOrUpdateUserInfo(record, true)">编辑品牌</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm cancel-text="否" ok-text="是"
                                  ok-type="danger"
                                  @confirm="() => {handleDeleteUser(record.key)}">
                      <template #icon>
                        <question-circle-outlined style="color: red"/>
                      </template>
                      <template #title>
                        <div>是否删除品牌？</div>
                        <a-tag class="my-2" color="red">{{ record.brandName }}</a-tag>
                      </template>
                      <a-button danger type="link">删除品牌</a-button>
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