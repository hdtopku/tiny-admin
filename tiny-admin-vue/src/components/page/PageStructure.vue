<template>
  <div>
    <Search :loading="loading" :search-form="searchParams" :top="84" @query-list="queryList" @open-modal="openModal"/>
    <slot name="content" :dataAndFunc="{dataSource,loading,pagination,
          queryList,changeRecordStatus,deleteRecordById,saveOrUpdate,openModal}"/>
  </div>
</template>
<script lang="ts" setup>
import Search from "@/components/page/Search.vue"
import {message} from "ant-design-vue"

const loading = ref(false), dataSource = ref([])
let pagination: any = {}
let {deleteById, saveOrUpdate, execQuery, openModal, searchParams={keyword: '', enabled: true, pageNum: 1, pageSize: 10}}: any = inject('init')

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
queryList()

const changeRecordStatus = (record: any) => {
  record.loading = true
  saveOrUpdate({id: record.id, enabled: !record.enabled}).then(() => {
    message.success('Success')
    record.enabled = !record.enabled
  }).finally(() => {
    record.loading = false
  })
}

const deleteRecordById = (id: string) => {
  deleteById(id).then(() => {
    message.success('Deleted successfully')
    queryList()
  })
}
</script>