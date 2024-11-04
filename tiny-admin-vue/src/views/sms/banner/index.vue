<template>
  <div>
    <Search :loading="loading" :top="84" placeholder="搜索轮播名称、备注" @open-modal="openModal"
            @query-list="queryList"/>
    <PcBannerList :dataSource="dataSource" :loading="loading" :pagination="pagination" class="hidden sm:block"
                  @open-modal="openModal" @query-list="queryList"/>
    <MobileBannerList :dataSource="dataSource" :loading="loading" :pagination="pagination" class="block sm:hidden"
                      @open-modal="openModal"
                      @query-list="queryList"/>
    <BannerModal @query-list="queryList" ref="modalRef"></BannerModal>
  </div>
</template>
<script lang="ts" setup>

import BannerModal from '@/views/sms/banner/BannerModal.vue'
import {getBannerPage, saveOrUpdateBanner} from '@/api/sms/banner.ts'
import Search from "@/components/Search.vue";
import PcBannerList from "@/views/sms/banner/PcBannerList.vue";
import MobileBannerList from "@/views/sms/banner/MobileBannerList.vue";
import {message} from "ant-design-vue";
import {t} from "@/utils/i18n.ts";

const openModal = (record: any) => {
  modalRef.value.openModal(record)
}

const loading = ref(false), dataSource = ref([]), modalRef = ref()
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getBannerPage(searchParams)
      .then((res: any) => {
        dataSource.value = res.records
        pagination = {current: res.current, pageSize: res.size, total: res.total,}
      })
      .finally(() => {
        loading.value = false
      })
}
const saveOrUpdate = (data: any) => {
  saveOrUpdateBanner(data).then(() => {
    record.status = rest.status
    message.success(t('操作成功'))
  }).finally(() => {
    record.loading = false
  })
}

</script>
