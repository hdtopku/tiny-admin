<script lang="ts" setup>
import {ref} from 'vue';
import {message} from "ant-design-vue";
import ImageCarousel from "@/views/pms/goods/ImageCarousel.vue";
import {getGoodsListByIds} from "@/api/pms/goods.ts";
import {removeGoods} from "@/api/sms/flashGoodsRel.ts";

const open = ref<boolean>(false);
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
const dataSource = ref([])

const selectedRowKeys: Ref<string[]> = ref([])
let originalGoodsIdSet
const queryList = () => {
  getGoodsListByIds(flashInfo.value.goodsIds).then((res: any) => {
    dataSource.value = res
  })
}

const handleOk = () => {
  formLoading.value = true
  selectedRowKeys.value.forEach(id => originalGoodsIdSet.delete(id))
  removeGoods(flashInfo.value.id, [...originalGoodsIdSet]).then(() => {
    message.success("操作成功")
    open.value = false
    emits('queryList')
  }).finally(() => {
    formLoading.value = false
  })
}

const openModal = (data: any = {}) => {
  if (data.id) {
    flashInfo.value = Object.assign({}, data)
  } else {
    flashInfo.value = {
      brandId: null,
      status: 1,
      sort: 9999,
      activityName: '',
      remark: '',
      goodsIds: [],
    }
  }
  open.value = true
  selectedRowKeys.value = [...flashInfo.value.goodsIds]
  originalGoodsIdSet = new Set(flashInfo.value.goodsIds)
  queryList()
}
const rowSelection: any = computed(() => {
  return {
    selectedRowKeys: unref(selectedRowKeys),
    getCheckboxProps: (record: any) => ({
      disabled: false,
      name: record.name,
    }),
    onChange: (allSelectedRowKeys: string[]) => {
      selectedRowKeys.value = allSelectedRowKeys
    },
    preserveSelectedRowKeys: true,
  };
})
defineExpose({
  openModal
})
const popConfirmVisible = ref(false)
const confirmClear = () => {
  selectedRowKeys.value = []
}
const handleTableChange = () => {

}
</script>
<template>
  <div>
    <a-modal v-model:open="open" cancel-text="取消" destroy-on-close
             ok-text="提交"
             @ok="handleOk">
      <template #title>
        移除秒杀商品 - {{ flashInfo.activityName }}
      </template>
      <template #footer>
        <a-space class="flex justify-end">
          <a-button key="back" @click="open = false">取消</a-button>
          <a-button key="submit" :loading="formLoading" type="primary" @click="handleOk">提交</a-button>
        </a-space>
      </template>
      <div class="mb-4">
        <a-tag>原有秒杀商品数：{{ flashInfo?.goodsIds?.length }}</a-tag>
        -
        <a-tag v-if="flashInfo.goodsIds.length - selectedRowKeys.length === 0" color="red"
               @close.prevent="popConfirmVisible = true">
          当前移除商品数：{{ flashInfo.goodsIds.length - selectedRowKeys.length }}
        </a-tag>
        <a-popconfirm
            v-else
            v-model:open="popConfirmVisible"
            :title="'是否重新选择想移除的商品？'"
            cancel-text="否"
            ok-text="是"
            @cancel="popConfirmVisible = false"
            @confirm="confirmClear"
        >
          <a-tag closable color="red" @close.prevent="popConfirmVisible = true">
            当前移除商品数：{{ flashInfo.goodsIds.length - selectedRowKeys.length }}
          </a-tag>
        </a-popconfirm>
        =
        <a-tag color="blue">剩余关联商品数：{{ selectedRowKeys.length }}</a-tag>
      </div>
      <a-input allow-clear placeholder="请输入商品名称、商品id、描述">
        <template #suffix>
          <a-button type="primary" @click="queryList">搜索</a-button>
        </template>
      </a-input>
      <a-table :columns="columns" :dataSource="dataSource" :row-selection="rowSelection" :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
               class="mt-4" row-key="id"
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

