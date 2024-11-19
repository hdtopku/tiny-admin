<script lang="ts" setup>
import { Pagination } from "ant-design-vue";
import { EditOutlined } from "@ant-design/icons-vue";
import SwitchStatusConfirm from "@/components/page/SwitchStatusConfirm.vue";

const {
  dataSource,
  pagination,
  loading,
  queryList = () => {},
  changeRecordStatus = () => {},
  deleteRecordById = () => {}
} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  changeRecordStatus: Function,
  deleteRecordById: Function
});
const emit = defineEmits(["openModal", "openAssignGoodsModal"]);
const openModal = (record: any) => {
  emit("openModal", record);
};
const handlePageChange = (pageNum: number, pageSize: number) => {
  queryList({ pageNum, pageSize });
};
</script>

<template>
  <a-list :data-source="dataSource" :loading="loading" :pagination="{...pagination, onChange: handlePageChange}">
    <template #renderItem="{ item:record }">
      <a-list-item>
        <a-card class="w-full">
          <template #actions>
            <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus(record)" />
            <DeleteRecordConfirm
                :record-id="record.id"
                :record-name="record.activityName"
                show-icon
                @confirm="deleteRecordById(record.id)"
            />
            <a-button type="link" @click="() => openModal(record)">
              <EditOutlined />
            </a-button>
          </template>
          <a-card-meta>
            <template #title>
              <div class="flex justify-between items-center">
                <ToolTip :length="10" :content="record.activityName" />
                <div>
                  <a-tag>
                    Total
                    <b class="text-red-500">{{ record?.goodsIds?.length }}</b>
                    items
                  </a-tag>
                  <a-button class="text-amber-500 -ml-1" type="link" @click="emit('openAssignGoodsModal', record)">
                    Assign Goods
                  </a-button>
                </div>
              </div>
            </template>
            <template #description>
              <a-typography-text>
                <ToolTip :content="record.remark" :length="300" :trigger="['click']" />
              </a-typography-text>
            </template>
          </a-card-meta>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>
