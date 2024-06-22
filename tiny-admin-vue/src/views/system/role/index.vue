<script setup lang="ts">

import {deleteById, getRolePage, saveOrUpdate} from "@/api/role.ts";
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import RoleModal from "@/views/system/role/RoleModal.vue";
import {useDebounceFn} from "@vueuse/core";
import {message} from "ant-design-vue";

const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0,
})
const searchForm = ref({
  keyword: '',
  pageNum: pagination.value.current,
  pageSize: pagination.value.pageSize,
  status: true,
})

const roleModalRef = ref<any>()
const switchLoading = ref(false)
const queryByStatus = () => {
  switchLoading.value = true
  queryList()
}
const dataSource = ref([])
const queryList = () => {
  getRolePage(searchForm.value).then((res: any) => {
    dataSource.value = res.records
    pagination.value = {
      current: res.current,
      pageSize: res.size,
      total: res.total,
    }
  }).catch(err => {
    switchLoading.value = false
    console.log(err)
    message.error('获取角色列表失败')
  }).finally(() => {
    switchLoading.value = false
  })
}
queryList()
const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)


const columns: any = [{
  title: '角色',
  dataIndex: 'roleName',
  key: 'roleName',
}, {
  title: '描述',
  dataIndex: 'description',
  key: 'description',
},
  {
    title: '操作',
    dataIndex: 'status',
    key: 'status',
    fixed: 'right',
    width: 160,
  },
]
const handleTableChange = () => {

}
const confirmChangeStatus = (record: any) => {
  record.loading = true
  saveOrUpdate({id:record.id, status: !record.status}).then(()=>{
    changeStatus(record)
  }).finally(()=>{
    record.loading = false
  })
}
const changeStatus = (record: any) => {
  record.status =!record.status
}
const saveOrUpdateRole = (isEdit = false, record={}) => {
  roleModalRef.value?.showModal(isEdit, isEdit ? record : {})
}
const deleteRole= (id: string) => {
  deleteById(id).then(() => {
    queryList()
    message.success('删除成功')
  })
}
</script>

<template>
  <div class="p-4">
    <div class="flex mb-4">
      <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
        <a-button type="primary" @click="()=>saveOrUpdateRole()">新增</a-button>
        <a-input @keydown.enter.prevent="queryList" allow-clear class="text-left"
                 placeholder="搜索角色名称" type="text"
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
    <a-table :scroll="{ x: 800, y: 300 }" @change="handleTableChange" :pagination="pagination" :columns="columns"
             :dataSource="dataSource">
      <template #bodyCell="{record, column}">
        <template v-if="column.dataIndex === 'status'">
          <div class="grid grid-cols-2 items-center justify-center">
            <a-popconfirm ok-text="是"
                          cancel-text="否" :title="record.status ? '是否禁用该角色？' : ' 是否启用该角色？'"
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
                    <a-button type="link" @click="() => saveOrUpdateRole(true, record)">编辑角色</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-button type="link">分配权限</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm @confirm="() => {deleteRole(record.id)}" ok-type="danger" ok-text="是" cancel-text="否" :title="`是否删除角色：${record.roleName}？`">
                      <template #icon>
                        <question-circle-outlined style="color: red"/>
                      </template>
                      <a-button type="link" danger>删除角色</a-button>
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
  <RoleModal @query-list="queryList" ref="roleModalRef"/>
</template>

<style scoped>

</style>