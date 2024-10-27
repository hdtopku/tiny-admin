<template>
  <div>
    <div class="p-4">
      <a-affix :offset-top="84">
        <Search :loading="loading" placeholder="搜索角色或描述" @open-modal="openModal" @query-list="queryList"/>
      </a-affix>
    </div>
    <PcRoleList :data-source="dataSource" :loading="loading" :pagination="pagination" @delete-by-id="deleteById"
                @open-modal="openModal"/>
    <RoleModal ref="modalRef" @query-list="queryList"/>
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {deleteById, getRolePage} from '@/api/system/role.ts'
import RoleModal from '@/views/system/role/RoleModal.vue'
import {message} from 'ant-design-vue'
import PcRoleList from "@/views/system/role/PcRoleList.vue";

const modalRef = ref<any>(), loading = ref(false), dataSource = ref([])
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getRolePage(searchParams)
      .then((res: any) => {
        dataSource.value = res.records
        pagination.value = {
          current: res.current,
          pageSize: res.size,
          total: res.total,
        }
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
</script>
