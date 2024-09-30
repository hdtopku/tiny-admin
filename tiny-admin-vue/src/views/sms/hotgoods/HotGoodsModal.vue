<script lang="ts" setup>
import {ref} from 'vue';
import {message} from "ant-design-vue";
import ImageCarousel from "@/views/pms/goods/ImageCarousel.vue";
import {getGoodsPage} from "@/api/pms/goods.ts";
import {useDebounceFn} from "@vueuse/core";
import {saveHotGoods} from "@/api/sms/hotGoods.ts";

const open = ref<boolean>(false);
const isUpdate = ref<boolean>(false);
const brandInfo = ref<any>();
const formLoading = ref(false)
const emits = defineEmits(['queryList'])

const columns: any = [
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
  }, {
    title: '商品id',
    dataIndex: 'goodsId',
    key: 'goodsId',
    width: 100,
    align: 'center',
  },
  {
    title: '促销价格',
    dataIndex: 'promotionPrice',
    key: 'promotionPrice',
    width: 100,
  },
  {
    title: '市场价格',
    dataIndex: 'marketPrice',
    key: 'marketPrice',
    width: 100,
  }
]
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
  getGoodsPage(searchForm.value).then((res: any) => {
    dataSource.value = res.records
    res.records.forEach((item: any) => {
      item.key = item.id
    })
    pagination.value.total = res.total
  })
}

const handleTableChange = (pagination: any) => {
  searchForm.value.pageNum = pagination.current
  searchForm.value.pageSize = pagination.pageSize
  queryList()
}

const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)

let selectedGoods: (string | number)[] = []
const rowSelection = ref({
  checkStrictly: false,
  onChange: (selectedRowKeys: (string | number)[], selectedRows: any[]) => {
    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
    selectedGoods = selectedRows
  },
  onSelect: (record: any, selected: boolean, selectedRows: any[]) => {
    console.log(record, selected, selectedRows);
  },
  onSelectAll: (selected: boolean, selectedRows: any[], changeRows: any[]) => {
    console.log(selected, selectedRows, changeRows);
  },
})

const handleOk = () => {
  const newGoodsList: any = []
  selectedGoods.forEach((item: any) => {
    newGoodsList.push({
      goodsId: item.id,
      remark: item.goodsName,
    })
  })
  formLoading.value = true
  saveHotGoods(newGoodsList).then(() => {
    message.success("操作成功");
    open.value = false
    emits('queryList')
  }).finally(() => {
    formLoading.value = false
  })
}
const showModal = (brand: any = {}) => {
  queryList()
  if (brand.id) {
    isUpdate.value = true;
    brandInfo.value = Object.assign({}, brand)
  } else {
    brandInfo.value = {
      brandId: null,
      status: 1,
      sort: 9999,
      remark: '',
    }
  }
  open.value = true
}
defineExpose({
  showModal
})

watch(brandInfo, (newVal) => {
  console.log(newVal)
})
</script>
<template>
  <div>
    <a-modal v-model:open="open" :title="isUpdate? '编辑热销商品' : '新增热销商品'" cancel-text="取消" destroy-on-close
             ok-text="提交"
             @ok="handleOk">
      <template #footer>
        <a-space class="flex justify-end">
          <a-button key="back" @click="open = false">取消</a-button>
          <a-button key="submit" :loading="formLoading" type="primary" @click="handleOk">提交</a-button>
        </a-space>
      </template>
      <a-input v-model:value="searchForm.keyword" allow-clear placeholder="请输入商品名称、商品id、描述">
        <template #suffix>
          <a-button type="primary" @click="queryList">搜索</a-button>
        </template>
      </a-input>
      <a-table :columns="columns" :dataSource="dataSource" :pagination="pagination" :row-selection="rowSelection"
               :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }" class="mt-4"
               @change="handleTableChange">
        <template #bodyCell="{record, column}">
          <template v-if="column.dataIndex === 'albumList'">
            <ImageCarousel :img-urls="record?.albumList || []" :width="100"/>
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
          <template v-else-if="column.dataIndex === 'goodsId'">
            <a-typography-text copyable>{{ record.id }}</a-typography-text>
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
        </template>
      </a-table>
    </a-modal>
  </div>
</template>

