<script lang="ts" setup>
import {DownOutlined} from "@ant-design/icons-vue"
import {t} from "@/utils/i18n.ts"
import {Pagination} from "ant-design-vue"
import ToolTip from "@/components/ToolTip.vue";
import EditNewGoodsModal from "@/views/sms/newgoods/EditNewGoodsModal.vue";
import ImageCarousel from "@/views/pms/goods/ImageCarousel.vue";
import DeleteRecordConfirm from "@/components/DeleteRecordConfirm.vue";
import SwitchStatusConfirm from "@/components/SwitchStatusConfirm.vue";

const {dataSource, pagination, loading} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
})

const emit = defineEmits(['openModal', 'queryList', 'changeRecordStatus', 'deleteRecordById'])

const handlePageChange = (current: number, pageSize: number) => {
  emit('queryList', {pageNum: current, pageSize})
}

const editModalRef = ref()
const columns: any = [
  {
    title: t('序号'),
    dataIndex: 'index',
    key: 'index',
    width: 60,
    align: 'center',
  },
  {
    title: '商品图片',
    dataIndex: 'albumPics',
    key: 'albumPics',
    width: 100,
    align: 'center',
  },
  {
    title: '商品名称',
    dataIndex: 'goodsName',
    key: 'goodsName',
    width: 200,
    align: 'center',
  },
  {
    title: t('备注'),
    dataIndex: 'remark',
    key: 'remark',
    width: 200,
    align: 'center',
  },
  {
    title: '市场价',
    dataIndex: 'marketPrice',
    key: 'marketPrice',
    width: 100,
    align: 'center',
  },
  {
    title: '促销价',
    dataIndex: 'promotionPrice',
    key: 'promotionPrice',
    width: 100,
    align: 'center',
  },
  {
    title: t('排序'),
    dataIndex: 'sort',
    key: 'sort',
    width: 100,
    align: 'center',
  },
  {
    title: t('操作'),
    key: 'operation',
    width: 160,
    fixed: 'right',
  },
]
</script>

<template>
  <div>
    <a-table :columns="columns" :dataSource="dataSource" :loading="loading" :pagination="pagination"
             :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }" @change="handlePageChange">
      <template #bodyCell="{ record, index, column }">
        <template v-if="column.key === 'index'">
          {{ index + 1 }}
        </template>
        <template v-else-if="column.dataIndex === 'albumPics'">
          <ImageCarousel :img-urls="record?.albumPics?.split(',')" :width="120"/>
        </template>
        <template v-else-if="column.dataIndex === 'goodsName'">
          <ToolTip :text="record.goodsName"/>
        </template>
        <template v-else-if="column.dataIndex === 'remark'">
          <ToolTip :text="record.remark"/>
        </template>

        <template v-else-if="column.key === 'operation'">
          <div class="grid grid-cols-2 items-center justify-center">
            <SwitchStatusConfirm :record="record" @change-record-status="emit('changeRecordStatus', record)"/>
            <a-dropdown placement="bottom" trigger="hover">
              <a-button class="flex items-center" size="small" type="link"
              >{{ $t('操作') }}
                <DownOutlined/>
              </a-button>
              <template #overlay>
                <a-menu class="text-center">
                  <a-menu-item>
                    <a-button type="link" @click="() => editModalRef.openModal(record)">编辑</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <DeleteRecordConfirm :record-id="record.id" :record-name="record.goodsName"
                                         @delete-record-by-id="emit('deleteRecordById', record.id)"/>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
        </template>
      </template>
    </a-table>

    <EditNewGoodsModal ref="editModalRef" @query-list="emit('queryList')"></EditNewGoodsModal>
  </div>
</template>