<script lang="ts" setup>
import {deleteById, execQuery, saveOrUpdate} from '@/api/system/user.ts'
import PcUserList from "@/views/system/user/PcUserList.vue";
import UserModal from "@/views/system/user/UserModal.vue";
import UserPasswordModal from "@/views/system/user/UserPasswordModal.vue";
import MobileUserList from "@/views/system/user/MobileUserList.vue";

const userModalRef = ref(), userPasswordModalRef = ref()

const openModal = (record = {username: '', nickname: '', email: '', phone: '',}) => {
  userModalRef.value.openModal(record)
}
const openPasswordModal = (record: any) => {
  userPasswordModalRef.value.openModal(record)
}
provide('init', {deleteById, saveOrUpdate, execQuery, openModal})

</script>

<template>
  <PageStructure>
    <template #content="{dataAndFunc}">
      <PcUserList class="hidden sm:block" v-bind="dataAndFunc" @open-password-modal="openPasswordModal"/>
      <MobileUserList class="block sm:hidden" v-bind="dataAndFunc" @open-password-modal="openPasswordModal"/>
      <UserModal ref="userModalRef" @queryList="dataAndFunc.queryList"/>
      <UserPasswordModal ref="userPasswordModalRef"/>
    </template>
  </PageStructure>
</template>
