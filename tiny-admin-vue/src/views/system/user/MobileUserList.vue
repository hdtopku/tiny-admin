<script lang="ts" setup>
import {Pagination} from "ant-design-vue";
import { EditOutlined, LockOutlined} from "@ant-design/icons-vue";

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
const handlePageChange = (pageNum: number, pageSize: number) => {
  queryList( {pageNum, pageSize})
}
</script>

<template>
  <div>
    <a-list :data-source="dataSource" :loading="loading"
            :pagination="{...pagination, onChange: handlePageChange}"
    >
      <template #renderItem="{ item:record }">
        <a-list-item>
          <a-card class="w-full">
            <template #actions>
              <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus"/>
              <DeleteRecordConfirm show-icon :record-name="record.username" :record-id="record.id" @confirm="deleteById"/>
              <a-button
                  class="text-orange-300 inline-flex items-center"
                  type="link"
                  @click="emit('openPasswordModal', record)"
              >
                <LockOutlined />
              </a-button>
              <a-button class="inline-flex items-center" type="link" @click="() => openModal(record)">
                <EditOutlined/>
              </a-button>

            </template>
            <a-card-meta>
              <template #title>
                {{ record.username }}
                <a-popover placement="bottom" title="Role list">
                  <template #content>
                    <a-tag v-for="roleName in record.roleNames">{{ roleName }}</a-tag>
                  </template>
                  <a-button v-if="record.roleNames.length" type="link">
                    {{ record.roleNames.length }} roles
                  </a-button>
                </a-popover>
                <a-tag v-if="!record.roleNames.length">
                  No roles assigned
                </a-tag>
              </template>
              <template #description>
                <div>
                  <a-tag>Nickname</a-tag>
                  <a-typography-text copyable>{{ record.nickname }}</a-typography-text>
                </div>
                <div>
                  <a-tag>Phone</a-tag>
                  <a-typography-text copyable>{{ record.phone }}</a-typography-text>
                </div>
                <div>
                  <a-tag>Email</a-tag>
                  <a-typography-text copyable>{{ record.email }}</a-typography-text>
                </div>
              </template>
              <template #avatar>
                <a-avatar :src="record.avatar" size="large"/>
              </template>
            </a-card-meta>
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>
