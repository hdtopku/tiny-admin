<script lang="ts" setup>
import { DownOutlined } from "@ant-design/icons-vue"
import { TableColumnProps } from "ant-design-vue"
import SwitchStatusConfirm from "@/components/page/SwitchStatusConfirm.vue"
import DeleteRecordConfirm from "@/components/page/DeleteRecordConfirm.vue"

const {
  dataSource,
  pagination,
  loading,
  openModal,
  changeRecordStatus,
  deleteById,
  queryList
} = defineProps({
  dataSource: {
    type: Array as PropType<Array<{ id: number, roleName: string, roleSize: number, description: string, createTime: string, updateTime: string, enabled: boolean }>>,
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

const handlePageChange = (current: number, pageSize: number) => {
  queryList?.({pageNum: current, pageSize})
}

const columns: TableColumnProps[] = [
  {
    title: 'Role',
    dataIndex: 'roleName',
    key: 'roleName'
  },
  {
    title: 'Permissions',
    dataIndex: 'roleSize',
    width: 120,
    key: 'roleSize'
  },
  {
    title: 'Description',
    width: 200,
    dataIndex: 'description',
    key: 'description'
  },
  {
    title: 'Creation Time',
    dataIndex: 'createTime',
    key: 'createTime'
  },
  {
    title: 'Update Time',
    dataIndex: 'updateTime',
    key: 'updateTime'
  },
  {
    title: 'Actions',
    dataIndex: 'enabled',
    key: 'enabled',
    fixed: 'right',
    width: 180
  }
]
</script>

<template>
  <div>
    <a-table
        :loading="loading"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
        :scroll="{ x: 'max-content', y: 'calc(100vh - 300px)' }"
        @change="handlePageChange"
    >
      <template #bodyCell="{ record, column }">
        <template v-if="column.dataIndex === 'enabled'">
          <div class="grid grid-cols-2 items-center">
            <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus?.(record)" />
            <a-dropdown>
              <a-button class="flex items-center" size="small" type="link">
                Actions
                <DownOutlined />
              </a-button>
              <template #overlay>
                <a-menu class="text-center">
                  <a-menu-item>
                    <a-button type="link" @click="openModal?.(record)">Edit Role</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <DeleteRecordConfirm :record-name="record.roleName" :record-id="record.id" @confirm="deleteById" />
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
            <a-button class="text-amber-500 -ml-1" type="link" @click="emit('openAssignRoleModal', record)">
              Assign Permissions
            </a-button>
          </div>
        </template>
        <template v-if="column.dataIndex === 'roleSize'">
          {{ record.menus.length }} items
        </template>
      </template>
    </a-table>
  </div>
</template>
