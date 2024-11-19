<script lang="ts" setup>
import { EditOutlined, UserAddOutlined } from "@ant-design/icons-vue"
import SwitchStatusConfirm from "@/components/page/SwitchStatusConfirm.vue"

const {
  dataSource,
  pagination,
  loading,
  changeRecordStatus,
  deleteById,
  openModal,
  queryList
} = defineProps({
  dataSource: {
    type: Array as PropType<Array<{ id: number, roleName: string, description: string, menus: Array<any> }>>,
    default: () => []
  },
  pagination: {
    type: Object as PropType<any>,
    default: () => ({})
  },
  loading: {
    type: Boolean,
    default: false
  },
  changeRecordStatus: Function,
  deleteById: Function,
  openModal: Function,
  queryList: Function
})

const emit = defineEmits(['openAssignRoleModal'])

const handlePageChange = (pageNum: number, pageSize: number) => {
  queryList?.({ pageNum, pageSize })
}
</script>

<template>
  <a-list :data-source="dataSource" :loading="loading" :pagination="{ ...pagination, onChange: handlePageChange }">
    <template #renderItem="{ item: record }">
      <a-list-item>
        <a-card class="w-full">
          <template #actions>
            <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus?.(record)" />
            <DeleteRecordConfirm show-icon :record-name="record.roleName" :record-id="record.id" @confirm="deleteById" />

            <a-button class="text-amber-500 -ml-1 inline-flex items-center" type="link" @click="emit('openAssignRoleModal', record)">
              <UserAddOutlined />
            </a-button>
            <a-button class="inline-flex items-center" type="link" @click="openModal?.(record)">
              <EditOutlined />
            </a-button>
          </template>
          <a-card-meta>
            <template #title>
              <div class="flex justify-between items-center">
                {{ record.roleName }}
                <a-tag>Has {{ record.menus.length }} permissions</a-tag>
              </div>
            </template>
            <template #description>
              <a-typography-text>{{ record.description }}</a-typography-text>
            </template>
          </a-card-meta>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>
