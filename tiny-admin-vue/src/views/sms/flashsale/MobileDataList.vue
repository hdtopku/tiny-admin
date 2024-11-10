<script lang="ts" setup>
import {Pagination} from "ant-design-vue";
import {EditOutlined} from "@ant-design/icons-vue";
import SwitchStatusConfirm from "@/components/SwitchStatusConfirm.vue";

const {
  dataSource, pagination, loading, queryList = () => {
  }, changeRecordStatus = () => {
  }, deleteRecordById = () => {
  }
} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  changeRecordStatus: Function,
  deleteRecordById: Function,
})
const emit = defineEmits(['openModal', 'openAssignRoleModal'])
const openModal = (record: any) => {
  emit('openModal', record)
}
const handlePageChange = (pageNum: number, pageSize: number) => {
  queryList({pageNum, pageSize})
}
</script>

<template>
  <a-list :data-source="dataSource" :loading="loading" :pagination="{...pagination, onChange: handlePageChange}">
    <template #renderItem="{ item:record }">
      <a-list-item>
        <a-card class="w-full">
          <template #actions>
            <SwitchStatusConfirm :record="record" @change-record-status="changeRecordStatus(record)"/>
            <DeleteRecordConfirm :record-id="record.id" :record-name="record.activityName" show-icon
                                 @delete-record-by-id="deleteRecordById(record.id)"/>
            <a-button type="link" @click="() => openModal(record)">
              <EditOutlined/>
            </a-button>
          </template>
          <a-card-meta>
            <template #title>
              <div class="flex justify-between items-center">
                <ToolTip :length="10" :text="record.activityName"/>
                <div>
                  <a-tag>共
                    <b class="text-red-500">{{ record?.goodsIds?.length }}</b>
                    件商品
                  </a-tag>
                  <a-button class="text-amber-500 -ml-1" type="link" @click="emit('openAssignGoodsModal', record)">
                    {{ $t('分配商品') }}
                  </a-button>
                </div>
              </div>
            </template>
            <template #description>
              <a-typography-text>{{ record.remark }}</a-typography-text>
            </template>
          </a-card-meta>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>