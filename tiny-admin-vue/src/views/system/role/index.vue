<template>
  <div>
    <a-affix :offset-top="84">
      <Search :loading="loading" placeholder="搜索角色或描述" @open-modal="openModal" @query-list="queryList"/>
    </a-affix>
    <PcRoleList :data-source="dataSource" :loading="loading" :pagination="pagination" class="hidden sm:block"
                @open-modal="openModal" @open-assign-role-modal="openAssignRoleModal" @query-list="queryList"/>
    <MobileRoleList :data-source="dataSource" :loading="loading" :pagination="pagination" class="block sm:hidden"
                    @open-modal="openModal" @open-assign-role-modal="openAssignRoleModal" @query-list="queryList"/>
    <RoleModal ref="modalRef" @query-list="queryList"/>
    <AssignRoleModal ref="assignRoleModalRef" @query-list="queryList"/>
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {getRolePage} from '@/api/system/role.ts'
import RoleModal from '@/views/system/role/RoleModal.vue'
import {message} from 'ant-design-vue'
import PcRoleList from "@/views/system/role/PcRoleList.vue"
import MobileRoleList from "@/views/system/role/MobileRoleList.vue"
import AssignRoleModal from "@/views/system/role/AssignRoleModal.vue"

const modalRef = ref<any>(), assignRoleModalRef = ref<any>(), loading = ref(false), dataSource = ref([])
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getRolePage(searchParams)
      .then((res: any) => {
        dataSource.value = res.records
        pagination = {current: res.current, pageSize: res.size, total: res.total}
      })
      .catch((err) => {
        message.error(t('获取角色列表失败') + err.message)
      })
      .finally(() => {
        loading.value = false
      })
}
const openModal = (record: any) => {
  modalRef.value.openModal(record)
}
const openAssignRoleModal = (record: any) => {
  assignRoleModalRef.value.openModal(record)
}
</script>
