<script lang="ts" setup>
import {message, Pagination} from "ant-design-vue";
import {DeleteOutlined, EditOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {deleteUserById, saveOrUpdateUser} from "@/api/system/user.ts";

const props: any = defineProps({
  dataSource: Array,
  pagination: Pagination,
  isLoading: Boolean
})
const emit = defineEmits(['openModal', 'queryList'])
const confirmChangeStatus = (record: any) => {
  record.status = !record.status
  saveOrUpdateUser(record).then(() => {
    emit('queryList')
  })
}
const openModal = (record: any) => {
  emit('openModal', record)
}
const handlePageChange = (pageNum: number, pageSize: number) => {
  emit('queryList', {pageNum, pageSize})
}
const deleteRecord = (id: number) => {
  deleteUserById(id).then(() => {
    message.success('删除成功')
    emit('queryList')
  })
}
</script>

<template>
  <div>
    <a-list :data-source="props.dataSource" :loading="props.isLoading"
            :pagination="{...props.pagination, onChange: handlePageChange}"
    >
      <template #renderItem="{ item:record }">
        <a-list-item>
          <a-card class="w-full">
            <template #actions>
              <a-popconfirm
                  :cancel-text="$t('否')"
                  :ok-text="$t('是')"
                  :title="
                record.status ? $t('是否禁用该用户？') : $t(' 是否启用该用户？')
              "
                  @confirm="confirmChangeStatus(record)"
              >
                <template #icon>
                  <question-circle-outlined style="color: red"/>
                </template>
                <a-switch
                    v-model:checked="record.status"
                    :checked-children="$t('已启用')"
                    :loading="record.loading"
                    :un-checked-children="$t('已禁用')"
                    class="!mt-2"
                    @click="() => {record.status =!record.status}"
                />
              </a-popconfirm>
              <a-popconfirm
                  :cancel-text="$t('否')"
                  :ok-text="$t('是')"
                  ok-type="danger"
                  @confirm="deleteRecord(record.id)"
              >
                <template #icon>
                  <question-circle-outlined style="color: red"/>
                </template>
                <template #title>
                  <div>{{ $t('是否删除用户？') }}</div>
                  <a-tag class="my-2" color="red">{{
                      record.username
                    }}
                  </a-tag>
                </template>
                <a-button danger type="link">
                  <DeleteOutlined/>
                </a-button>
              </a-popconfirm>
              <a-button type="link" @click="() => openModal(record)">
                <EditOutlined/>
              </a-button>

            </template>
            <a-card-meta>
              <template #title>
                {{ record.username }}
                <a-popover placement="bottom" title="角色列表">
                  <template #content>
                    <a-tag v-for="roleName in record.roleNames">{{ roleName }}</a-tag>
                  </template>
                  <a-button v-if="record.roleNames.length" type="link">
                    {{ record.roleNames.length }}个角色
                  </a-button>
                </a-popover>
                <a-tag v-if="!record.roleNames.length">
                  暂未分配角色
                </a-tag>
              </template>
              <template #description>
                <div>
                  <a-tag>昵称</a-tag>
                  <a-typography-text copyable>{{ record.nickname }}</a-typography-text>
                </div>
                <div>
                  <a-tag>手机</a-tag>
                  <a-typography-text copyable>{{ record.phone }}</a-typography-text>
                </div>
                <div>
                  <a-tag>邮箱</a-tag>
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