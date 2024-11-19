<template>
  <a-table
      :columns="columns"
      :dataSource="dataSource"
      :pagination="pagination"
      :loading="loading"
      :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
      @change="handleTableChange"
  >
    <template #bodyCell="{ record, index, column }">
      <template v-if="column.key === 'index'">
        {{ index + 1 }}
      </template>
      <template v-if="column.dataIndex === 'avatar'">
        <a-avatar :src="record.avatar" shape="square" size="large" />
      </template>
      <template v-if="column.dataIndex === 'roleNames'">
        <span v-if="record.roleNames?.length">
          <a-tag v-for="item in record.roleNames" :key="item">
            {{ item }}
          </a-tag>
        </span>
        <span v-else class="text-gray-400 text-xs">
          No role assigned
        </span>
      </template>
      <template v-if="column.dataIndex === 'enabled'">
        <div class="grid grid-cols-2 items-center justify-center">
          <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus"/>
          <a-dropdown>
            <a-button class="flex items-center" size="small" type="link">
              Actions
              <DownOutlined />
            </a-button>
            <template #overlay>
              <a-menu class="text-center">
                <a-menu-item>
                  <a-button type="link" @click="() => openModal(record)">
                    Edit User
                  </a-button>
                </a-menu-item>
                <a-menu-item>
                  <a-button
                      class="text-orange-300"
                      type="link"
                      @click="emit('openPasswordModal', record)"
                  >
                    Reset Password
                  </a-button>
                </a-menu-item>
                <a-menu-item>
                  <DeleteRecordConfirm :record-id="record.id" :record-name="record.username" @confirm="deleteById"/>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </template>
    </template>
  </a-table>
</template>

<script lang="ts" setup>
import { DownOutlined } from "@ant-design/icons-vue";
import { Pagination } from "ant-design-vue";

const {dataSource, pagination, loading, queryList=()=>{}, changeRecordStatus=()=>{}, deleteById=()=>{}, openModal=()=>{}} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  changeRecordStatus: Function,
  deleteById: Function,
  openModal: Function,
  queryList: Function
})
const emit = defineEmits(["openPasswordModal"]);
const handleTableChange = (pagination: any) => {
  queryList( {pageNum: pagination.current, pageSize: pagination.pageSize})
}

const columns: any = [
  {
    title: "Index",
    key: "index",
    width: 60,
  },
  {
    title: "Avatar",
    dataIndex: "avatar",
    key: "avatar",
    width: 80,
  },
  {
    title: "Username",
    dataIndex: "username",
    key: "username",
    width: 80,
  },
  {
    title: "Nickname",
    dataIndex: "nickname",
    key: "nickname",
    width: 120,
  },
  {
    title: "Roles",
    dataIndex: "roleNames",
    key: "roleNames",
    width: 120,
  },
  {
    title: "Email",
    dataIndex: "email",
    key: "email",
    width: 60,
  },
  {
    title: "Phone Number",
    dataIndex: "phone",
    key: "phone",
    width: 80,
  },
  {
    title: "Created At",
    dataIndex: "createTime",
    key: "createTime",
  },
  {
    title: "Updated At",
    dataIndex: "updateTime",
    key: "updateTime",
  },
  {
    title: "Actions",
    dataIndex: "enabled",
    key: "enabled",
    fixed: "right",
    width: 180,
  },
];
</script>
