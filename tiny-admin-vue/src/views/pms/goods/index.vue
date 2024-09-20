<script lang="ts" setup>
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {getGoodsPage} from "@/api/goods.ts";
import ImageCarousel from "@/views/pms/goods/ImageCarousel.vue";
import {useDebounceFn} from "@vueuse/core";
import GoodsModal from "@/views/pms/goods/GoodsModal.vue";

const handleAdd = () => {

}
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

const queryList = () => {
  getGoodsPage(searchForm.value).then(res => {
    dataSource.value = res.records
  })
}
queryList()
const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)
const queryByStatus = () => {
}

const columns: any = [
  {
    title: '序号',
    key: 'index',
    width: 60,
  },
  {
    title: '商品图册',
    dataIndex: 'albumList',
    key: 'albumList',
    width: 100,
  },
  {
    title: '商品名称',
    dataIndex: 'goodsName',
    key: 'goodsName',
    width: 150,
  },
  {
    title: '商品简介',
    dataIndex: 'goodsDesc',
    key: 'goodsDesc',
    width: 150,
  },
  {
    title: '市场价格',
    dataIndex: 'marketPrice',
    key: 'marketPrice',
    width: 100,
  },
  {
    title: '促销价格',
    dataIndex: 'promotionPrice',
    key: 'promotionPrice',
    width: 100,
  },
  {
    title: '操作',
    key: 'operation',
    fixed: 'right',
    width: 160,
  }
]
const dataSource = ref([])
const handleTableChange = (pagination: any) => {
  searchForm.value.pageNum = pagination.current
  searchForm.value.pageSize = pagination.pageSize
  queryList()
}

const goodsModalRef = ref()
const handleEdit = (record: any) => {
  goodsModalRef.value.showModal(record)
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
                   placeholder="搜索商品名称、副标题、简介、品牌名"
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
        <template v-else-if="column.dataIndex === 'albumList'">
          <ImageCarousel :img-urls="record?.albumList || []"/>
        </template>
        <template v-else-if="column.dataIndex === 'goodsName'">
          <a-tooltip :arrow="false">
            <template #title>
              <span>{{ record.goodsName }}</span>
            </template>
            <span>{{ record.goodsName?.substring(0, 10) }}</span>
            <span v-if="record.goodsName?.length > 10">...</span>
          </a-tooltip>
        </template>
        <template v-else-if="column.dataIndex === 'goodsDesc'">
          <a-tooltip :arrow="false">
            <template #title>
              <span>{{ record.goodsDesc }}</span>
            </template>
            <span>{{ record.goodsDesc?.substring(0, 10) }}</span>
            <span v-if="record.goodsDesc?.length > 10">...</span>
          </a-tooltip>
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
                        class="flex-shrink-0"
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
                    <a-button type="link" @click="() => handleEdit(record)">编辑商品</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm cancel-text="否" ok-text="是"
                                  ok-type="danger"
                                  @confirm="() => {deleteBrand(record.id)}">
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
    <GoodsModal ref="goodsModalRef"/>
  </div>
</template>

<style scoped>


</style>