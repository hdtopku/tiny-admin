<script lang="ts" setup>
import {ref} from 'vue';
import {message} from "ant-design-vue";
import ImageCarousel from "@/views/pms/goods/ImageCarousel.vue";
import {getGoodsPage} from "@/api/pms/goods.ts";
import {useDebounceFn} from "@vueuse/core";
import {assignGoods} from "@/api/sms/flashGoodsRel.ts";

const open = ref<boolean>(false);
const isUpdate = ref<boolean>(false);
const flashInfo: Ref<any> = ref({});
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
  pageSize: 2,
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
    res.records.forEach((item: any) => {
      item.key = item.id
      item.disabled = originalSelectedGoodsIdSet.value.has(item.id)
    })
    res.records.sort((a, b) => {
      if (!a.sort) a.sort = 99999
      if (!b.sort) b.sort = 99999
      if (a.disabled && b.disabled || !a.disabled && !b.disabled) {
        return a.sort - b.sort
      }
      if (a.disabled) return 1
      return -1
    })
    dataSource.value = res.records
    pagination.value.total = res.total
    pagination.value.current = res.current
  })
}

const handleTableChange = (pagination: any) => {
  searchForm.value.pageNum = pagination.current
  searchForm.value.pageSize = pagination.pageSize
  queryList()
}

const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)

const originalSelectedGoodsIds: Ref<string[]> = ref([])
const originalSelectedGoodsIdSet = ref()

const selectedRowKeys: Ref<string[]> = originalSelectedGoodsIds
const rowSelection: any = computed(() => {
  return {
    selectedRowKeys: unref(selectedRowKeys),
    getCheckboxProps: (record: any) => ({
      disabled: originalSelectedGoodsIdSet.value.has(record.id), // Column configuration not to be checked
      name: record.name,
    }),
    onChange: (allSelectedRowKeys: string[]) => {
      selectedRowKeys.value = allSelectedRowKeys
    },
    preserveSelectedRowKeys: true,
  };
});

const handleOk = () => {
  formLoading.value = true
  assignGoods(flashInfo.value.id, selectedRowKeys.value).then(() => {
    message.success("操作成功")
    open.value = false
    emits('queryList')
  }).finally(() => {
    formLoading.value = false
  })
}

const openModal = (data: any = {}) => {
  if (data.id) {
    isUpdate.value = true;
    flashInfo.value = Object.assign({}, data)
    originalSelectedGoodsIds.value = data.goodsIds
    originalSelectedGoodsIdSet.value = new Set(data.goodsIds)
  } else {
    flashInfo.value = {
      brandId: null,
      status: 1,
      sort: 9999,
      activityName: '',
      remark: '',
    }
  }
  open.value = true
  if (dataSource.value.length === 0) queryList()
}
defineExpose({
  openModal
})
const popConfirmVisible = ref(false)
const confirmClear = () => {
  selectedRowKeys.value = [...originalSelectedGoodsIdSet.value]
}
</script>
<template>
  <div>
    <a-modal v-model:open="open" cancel-text="取消" destroy-on-close
             ok-text="提交"
             @ok="handleOk">
      <template #title>
        添加秒杀商品 - {{ flashInfo.activityName }}
      </template>
      <template #footer>
        <a-space class="flex justify-end">
          <a-button key="back" @click="open = false">取消</a-button>
          <a-button key="submit" :loading="formLoading" type="primary" @click="handleOk">提交</a-button>
        </a-space>
      </template>
      <div class="mb-4">
        <a-tag>原有秒杀商品数：{{ originalSelectedGoodsIdSet.size }}</a-tag>
        +
        <a-popconfirm
            v-model:open="popConfirmVisible"
            cancel-text="否"
            ok-text="是"
            title="是否清空重新选择?"
            @cancel="popConfirmVisible = false"
            @confirm="confirmClear"
        >
          <a-tag closable color="red" @close.prevent="popConfirmVisible = true">
            当前选择商品数：{{ selectedRowKeys.length - originalSelectedGoodsIdSet.size }}
          </a-tag>
        </a-popconfirm>
        =
        <a-tag color="blue">合计商品数：{{ selectedRowKeys.length }}</a-tag>
      </div>
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

