<script setup lang="ts">
import {deleteUser, getUserPage, saveOrUpdate} from "@/api/user.ts";
import {useDebounceFn} from "@vueuse/core";
import {DownOutlined, QuestionCircleOutlined} from '@ant-design/icons-vue';
import UserModal from "@/views/system/user/UserModal.vue";
import UserPasswordModal from "@/views/system/user/UserPasswordModal.vue";
import {message} from "ant-design-vue";

const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0,
})
const searchForm = ref({
  keyword: '',
  status: true,
  pageNum: pagination.value.current,
  pageSize: pagination.value.pageSize,
})
const columns = [{
  title: '用户名',
  dataIndex: 'username',
  key: 'username',
  width: 80,
}, {
  title: '昵称',
  dataIndex: 'nickname',
  key: 'nickname',
  width: 60,
},
  {
    title: '邮箱',
    dataIndex: 'email',
    key: 'email',
    width: 60,
  },
  {
    title: '手机号',
    dataIndex: 'phone',
    key: 'phone',
    width: 80,
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime',
  },
  {
    title: '更新时间',
    dataIndex: 'updateTime',
    key: 'updateTime',
  },
  {
    title: '操作',
    dataIndex: 'status',
    key: 'status',
    fixed: 'right',
    width: 160,
  },
]
const dataSource = ref([])
const switchLoading = ref(false)
const queryList = () => {
  getUserPage(searchForm.value).then((res: any) => {
    dataSource.value = res.records
    pagination.value = {
      current: res.current,
      pageSize: res.size,
      total: res.total,
    }
  }).finally(() => {
    switchLoading.value = false
  })
}
queryList()
const handleTableChange = (pagination: any) => {
  searchForm.value.pageNum = pagination.current
  searchForm.value.pageSize = pagination.pageSize
  queryList()
}
const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)
const changeStatus = (record: any) => {
  record.status = !record.status
}

const confirmChangeStatus = (record: any) => {
  record.loading = true
  saveOrUpdate({id: record.key, status: !record.status}).then(() => {
    changeStatus(record)
  }).finally(() => {
    record.loading = false
  })
}
const queryByStatus = () => {
  switchLoading.value = true
  queryList()
}

const defaultUserInfo = {
  username: '',
  nickname: '',
  email: '',
  phone: '',
}
const editUserInfo = ref(defaultUserInfo)
const UserModalRef = ref()
const saveOrUpdateUserInfo = (record: any, isEdit = false) => {
  editUserInfo.value = Object.assign({}, record)
  UserModalRef.value.showModal(Object.assign({}, isEdit ? record : defaultUserInfo), isEdit)
}
const UserPasswordModalRef = ref()
const handleChangePassword = (record: any) => {
  UserPasswordModalRef.value.showModal(record)
}
const handleDeleteUser = (id: string) => {
  deleteUser(id).then(() => {
    queryList()
    message.success('删除成功')
  })
}
</script>

<template>
  <div class="p-4">
    <div class="flex mb-4">
      <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
        <a-button type="primary" @click="saveOrUpdateUserInfo">新增</a-button>
        <a-input @keyup.enter.native="queryList" allow-clear class="text-left"
                 placeholder="搜索用户名、昵称、邮箱、手机号" type="text"
                 id="keyword"
                 v-model:value="searchForm.keyword" autocomplete="off">
          <template #prefix>
            <a-switch :loading="switchLoading" @change="queryByStatus" class="flex-shrink-0"
                      v-model:checked="searchForm.status" checked-children="已启用" un-checked-children="已禁用"/>
          </template>
          <template #suffix>
            <a-button type="primary" @click="queryList">搜索</a-button>
          </template>
        </a-input>
      </div>
    </div>
    <a-table :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }" @change="handleTableChange" :pagination="pagination"
             :columns="columns"
             :dataSource="dataSource">
      <template #bodyCell="{record, column}">
        <template v-if="column.dataIndex === 'status'">
          <div class="grid grid-cols-2 items-center justify-center">
            <a-popconfirm ok-text="是"
                          cancel-text="否" :title="record.status ? '是否禁用该用户？' : ' 是否启用该用户？'"
                          @confirm="() => {confirmChangeStatus(record)}">
              <template #icon>
                <question-circle-outlined style="color: red"/>
              </template>
              <a-switch size="small" :loading="record.loading" @click="() => {changeStatus(record)}"
                        class="flex-shrink-0"
                        v-model:checked="record.status" checked-children="已启用" un-checked-children="已禁用"/>
            </a-popconfirm>
            <a-dropdown>
              <a-button size="small" type="link" class="flex items-center">
                操作
                <DownOutlined/>
              </a-button>
              <template #overlay>
                <a-menu class="text-center">
                  <a-menu-item>
                    <a-button type="link" @click="() => saveOrUpdateUserInfo(record, true)">编辑用户</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-button type="link" @click="handleChangePassword(record)" class="text-orange-300">重置密码
                    </a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-button type="link">更换头像</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-button type="link">分配角色</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm ok-type="danger" ok-text="是" cancel-text="否" :title="`是否删除用户(${record.username})？`" @confirm="() => {handleDeleteUser(record.key)}">
                      <template #icon>
                        <question-circle-outlined style="color: red"/>
                      </template>
                      <a-button type="link" danger>删除用户</a-button>
                    </a-popconfirm>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
        </template>
      </template>
    </a-table>
  </div>
  <UserModal @queryList="queryList" ref="UserModalRef" :userInfo="editUserInfo"/>
  <UserPasswordModal ref="UserPasswordModalRef"/>
</template>

<style scoped>

</style>
