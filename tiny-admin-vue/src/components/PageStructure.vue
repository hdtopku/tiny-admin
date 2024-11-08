<template>
  <div>
    <Search :loading="loading" :search-form="searchParams" :top="84" @query-list="queryList" @open-modal="openModal"/>
    <slot name="content" :dataAndFunc="{dataSource,loading,pagination,
          queryList,changeRecordStatus,deleteRecordById,saveOrUpdate,}"/>
  </div>
</template>
<script lang="ts" setup>
import Search from "@/components/Search.vue"
import {message} from "ant-design-vue"
import {t} from "@/utils/i18n.ts"

const loading = ref(false), dataSource = ref([])
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}
const {deleteById, saveOrUpdate, execQuery, openModal}: any = inject('init')

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  execQuery(searchParams)
      .then((res: any) => {
        dataSource.value = res.records
        pagination = {current: res.current, pageSize: res.size, total: res.total}
      })
      .finally(() => {
        loading.value = false
      })
}

const changeRecordStatus = (record: any) => {
  record.loading = true
  saveOrUpdate({id: record.id, status: !record.status}).then(() => {
    message.success(t('操作成功'))
    record.status = !record.status
  }).finally(() => {
    record.loading = false
  })
}

const deleteRecordById = (id: string) => {
  deleteById(id).then(() => {
    message.success(t('删除成功'))
    queryList()
  })
}
</script>