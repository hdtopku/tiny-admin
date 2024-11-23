<script setup lang="ts">
import {CreateIcon} from "@/components/CustomIcon.ts"
import {deleteMenuById} from "@/api/system/menu.ts"
import {message} from "ant-design-vue"

const {dataSource, loading} = defineProps({
  dataSource: Array,
  loading: Boolean,
})

const emit = defineEmits(['queryList', 'openModal'])
const deleteMenu = (menuId: String) => {
  deleteMenuById(menuId).then(() => {
    emit('queryList')
    message.success('Delete successful')
  })
}
const columns: any = [
  {
    title: 'Menu Name',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: 'Type',
    dataIndex: 'type',
    key: 'type',
    width: 100,
  },
  {
    title: 'Order',
    dataIndex: 'sort',
    key: 'sort',
    width: 80,
  },
  {
    title: 'Permission',
    dataIndex: 'permission',
    key: 'permission',
    width: 120,
  },
  {
    title: 'Button Strategy',
    dataIndex: 'unauthorizedStrategy',
    key: 'permission',
    width: 120,
  },
  {
    title: 'Creation Time',
    dataIndex: 'createTime',
    key: 'createTime',
  },
  {
    title: 'Update Time',
    dataIndex: 'updateTime',
    key: 'updateTime',
  },
  {
    title: 'Actions',
    key: 'operation',
    fixed: 'right',
  },
]
</script>

<template>
  <div>
    <div class="flex justify-end">
      <a-button class="m-2 mr-4" type="primary" @click="emit('openModal')">Add</a-button>
    </div>
    <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
        row-key="id"
        :pagination="false"
    >
      <template #bodyCell="{ record, column }">
        <template v-if="column.dataIndex === 'name'">
          <CreateIcon :icon="record.icon" />
          {{ record.name }}
        </template>
        <template v-if="column.dataIndex === 'type'">
          <a-tag color="blue" v-if="record.type === 1">Menu</a-tag>
          <a-tag color="red" v-if="record.type === 2">Button</a-tag>
        </template>
        <template v-if="column.key === 'operation'">
          <a-button type="link" @click="emit('openModal', record)">Edit</a-button>
          <Auth :has-permission="'system:sysmenu:deletebyids'">
            <DeleteRecordConfirm :record-id="record.id" :record-name="record.name" @confirm="deleteMenu" />
          </Auth>
          <a-button
              v-show="record.type === 1"
              link
              type="link"
              @click="() => emit('openModal', { parentId: record.id })"
          >
            Add
          </a-button>
        </template>
      </template>
    </a-table>
  </div>
</template>

<style scoped>
</style>
