<script lang="ts" setup>
import {getUserPage} from '@/api/system/user.ts'
import PcUserList from "@/views/system/user/PcUserList.vue";
import UserModal from "@/views/system/user/UserModal.vue";
import UserPasswordModal from "@/views/system/user/UserPasswordModal.vue";
import MobileUserList from "@/views/system/user/MobileUserList.vue";

const loading = ref(false), dataSource = ref([]), userModalRef = ref(), userPasswordModalRef = ref()
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getUserPage(searchParams)
      .then((res: any) => {
        dataSource.value = res.records
        pagination = {current: res.current, pageSize: res.size, total: res.total}
      })
      .finally(() => {
        loading.value = false
      })
}
const openModal = (record = {username: '', nickname: '', email: '', phone: '',}) => {
  userModalRef.value.openModal(record)
}
const openPasswordModal = (record: any) => {
  userPasswordModalRef.value.openModal(record)
}
</script>

<template>
  <div>
    <a-affix :offset-top="84">
      <Search :loading="loading" placeholder="搜索用户名、昵称、邮箱、手机号" @open-modal="openModal"
              @query-list="queryList"/>
    </a-affix>
    <PcUserList :dataSource="dataSource" :loading="loading" :pagination="pagination" class="hidden sm:block"
                @query-list="queryList" @open-modal="openModal" @open-password-modal="openPasswordModal"/>
    <MobileUserList :dataSource="dataSource" :loading="loading" :pagination="pagination" class="block sm:hidden"
                    @query-list="queryList" @open-modal="openModal" @open-password-modal="openPasswordModal"/>
    <UserModal ref="userModalRef" @queryList="queryList"/>
    <UserPasswordModal ref="userPasswordModalRef"/>
  </div>
</template>
