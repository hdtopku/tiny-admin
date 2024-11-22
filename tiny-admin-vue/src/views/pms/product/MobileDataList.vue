<script lang="ts" setup>
import { EditOutlined, ExpandAltOutlined } from '@ant-design/icons-vue'
import { Pagination } from "ant-design-vue"
import ImageCarousel from "@/views/pms/product/ImageCarousel.vue"

const { dataSource, pagination, loading, queryList, changeRecordStatus, deleteRecordById, openModal } = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  changeRecordStatus: Function,
  deleteRecordById: Function,
  openModal: Function,
})
const emit = defineEmits(['openPreview'])

const handlePageChange = (pageNum: number, pageSize: number) => {
  queryList?.({ pageNum, pageSize })
}
</script>

<template>
  <a-list :data-source="dataSource" :loading="loading" :pagination="{...pagination, onChange: handlePageChange}"
          item-layout="horizontal">
    <template #renderItem="{ item: record }">
      <a-list-item>
        <a-card class="w-full">
          <template #cover>
            <div class="bg-gray-100 dark:bg-gray-800 h-48">
              <ImageCarousel :img-urls="record?.album || []" class="mx-auto" />
            </div>
          </template>
          <template #actions>
            <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus?.(record)" />
            <DeleteRecordConfirm show-icon :record-id="record.id" :record-name="record.productName"
                                 @confirm="deleteRecordById?.(record.id)" />
            <a-button type="link" @click="emit('openPreview', record)">
              <ExpandAltOutlined />
            </a-button>
            <a-button type="link" @click="openModal?.(record)">
              <EditOutlined />
            </a-button>
          </template>
          <a-card-meta>
            <template #title>
              <ToolTip :class-name="'font-bold'" :content="record.productName" :length="50"/>
            </template>
            <template #description>
<!--              <div>{{ record.productDesc }}</div>-->
              <div class="mt-2">
                <a-tag>Market Price</a-tag>
                {{ record.marketPrice }}
                <a-divider type="vertical" />
                <a-tag>Sale Price</a-tag>
                {{ record.salePrice }}
                <a-divider type="vertical" />
                <a-tag>Stock</a-tag>
                {{ record.stock }}
              </div>
            </template>
          </a-card-meta>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>
