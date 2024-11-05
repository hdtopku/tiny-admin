<template>
  <div>
    <a-drawer v-model:open="open" :cancel-text="$t('取消')" :ok-text="$t('提交')"
              :size="width<768 ? 'default' : 'large'"
              destroy-on-close title="新增商品" @ok="handleOk">
      <template #footer>
        <a-space class="flex justify-end">
          <a-button key="back" @click="open = false">{{ $t('取消') }}</a-button>
          <a-button
              key="submit"
              :loading="loading"
              type="primary"
              @click="handleOk"
          >{{ $t('提交') }}
          </a-button
          >
        </a-space>
      </template>
      <Search :loading="loading" search-class="" @query-list="queryList"/>
      <a-table
          :columns="columns"
          :dataSource="dataSource"
          :pagination="pagination"
          :row-selection="rowSelection"
          class="mt-4"
          @change="handleTableChange"
          :loading="loading"
      >
        <template #bodyCell="{ record, column }">
          <template v-if="column.dataIndex === 'albumList'">
            <ImageCarousel :img-urls="record?.albumList || []" :width="100"/>
          </template>
          <template v-else-if="column.dataIndex === 'goodsName'">
            <ToolTip :length="10" :text="record.goodsName"/>
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
    </a-drawer>
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {ref} from 'vue'
import {message} from 'ant-design-vue'
import ImageCarousel from '@/views/pms/goods/ImageCarousel.vue'
import {getGoodsPage} from '@/api/pms/goods.ts'
import {useDebounceFn, useWindowSize} from '@vueuse/core'
import {getAllNewGoodsIds} from "@/api/sms/newGoods.ts";

const {width} = useWindowSize()

const open = ref<boolean>(false), isUpdate = ref<boolean>(false), brandInfo = ref<any>()
const emit = defineEmits(['queryList']), loading = ref(false), dataSource = ref([])
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getGoodsPage(searchParams).then((res: any) => {
    dataSource.value = res.records
    res.records.forEach((item: any) => {
      item.key = item.id
    })
    pagination = {current: res.current, pageSize: res.size, total: res.total}
  }).finally(() => {
    loading.value = false
  })
}

const handleTableChange = (pagination: any) => {
  searchParams.pageNum = pagination.current
  searchParams.pageSize = pagination.pageSize
  queryList()
}

const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchParams.keyword, debounceQuery)

const selectedGoodsIds: Ref<(string | number)[]> = ref([])
const rowSelection = computed(() => {
  return {
    checkStrictly: false,
    selectedRowKeys: unref(selectedGoodsIds),
    preserveSelectedRowKeys: true,
    onSelectAll: (selected, selectedRows, changeRows) => {
      if (selected) {
        selectedGoodsIds.value.push(...changeRows.map((item: any) => item.id))
      } else {
        selectedGoodsIds.value = selectedGoodsIds.value.filter((id: any) => !changeRows.some((item: any) => item.id === id))
      }
    },
    onSelect: (record, selected: boolean) => {
      if (selected) {
        selectedGoodsIds.value.push(record.id)
      } else {
        selectedGoodsIds.value = selectedGoodsIds.value.filter((id: any) => id !== record.id)
      }
    }
  }
})

let submit: Function
const handleOk = () => {
  loading.value = true
  submit(selectedGoodsIds.value)
      .then(() => {
        message.success(t('操作成功'))
        open.value = false
        emit('queryList')
      })
      .finally(() => {
        loading.value = false
      })
}
defineExpose({
  openModal: (record: any = {}, getSelectIds: Function, submitFunc: Function) => {
    open.value = true
    if (!dataSource.value?.length) {
      queryList()
    }
    getSelectIds().then((res: any) => {
      selectedGoodsIds.value = res
    })
    isUpdate.value = !!record.id
    isUpdate.value = true
    brandInfo.value = {...{brandId: null, status: 1, sort: 9999, remark: '',}, ...record}
    submit = submitFunc
  }
})

const columns: any = [
  {
    title: t('商品图册'),
    dataIndex: 'albumList',
    key: 'albumList',
    width: 100,
  },
  {
    title: t('商品名称'),
    dataIndex: 'goodsName',
    key: 'goodsName',
    width: 150,
  },
  {
    title: t('促销价'),
    dataIndex: 'promotionPrice',
    key: 'promotionPrice',
    width: 100,
  },
  {
    title: t('市场价'),
    dataIndex: 'marketPrice',
    key: 'marketPrice',
    width: 100,
  },
]
</script>
