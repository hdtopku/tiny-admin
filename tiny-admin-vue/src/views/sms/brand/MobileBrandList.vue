<script lang="ts" setup>
import {message, Pagination, PaginationProps} from "ant-design-vue";
import {DeleteOutlined, EditOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {deleteSmsBrandById, saveOrUpdateSmsBrand} from "@/api/sms/brand.ts";
import {t} from "@/utils/i18n.ts";

const {dataSource, pagination, loading} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean
})
const emit = defineEmits(['openModal', 'queryList'])
const confirmChangeStatus = (record: any) => {
  record.loading = true
  const {loading, brandName, logo, createTime, updateTime, ...rest} = record
  rest.status = !rest.status
  saveOrUpdateSmsBrand(rest).then(() => {
    record.status = rest.status
    message.success(t('操作成功'))
  }).finally(() => {
    record.loading = false
  })
}
const handleTableChange = (pagination: PaginationProps) => {
  emit('queryList', {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  })
}
const deleteRecordById = (id: string) => {
  deleteSmsBrandById(id).then(() => {
    message.success(t('删除成功'))
    emit('queryList')
  })
}
</script>

<template>
  <div>
    <a-list :data-source="dataSource" :loading="loading"
            :pagination="{pagination, onChange: handleTableChange}"
            item-layout="horizontal">
      <template #renderItem="{ item:record }">
        <a-list-item>
          <a-list-item-meta>
            <template #description>
              <a-tag>备注</a-tag>
              <a-typography-text :content="record.remark" :ellipsis="{ rows: 3, expandable: true, symbol: 'more' }"/>
            </template>
            <template #title>
              <div class="flex justify-between items-center">
                <a-tooltip :title="record.brandStory" placement="bottomLeft">
                  <a-button size="small" type="link">{{ record.brandName }}</a-button>
                </a-tooltip>
                <div class="flex justify-start items-center">
                  <a-popconfirm :cancel-text="$t('否')" :ok-text="$t('是')"
                                :title="record.status ? '是否禁用该推荐品牌？' : '是否启用该推荐品牌？'"
                                @confirm="() => {confirmChangeStatus(record)}">
                    <template #icon>
                      <question-circle-outlined style="color: red"/>
                    </template>
                    <a-switch
                        v-model:checked="record.status"
                        :checked-children="$t('已启用')"
                        :loading="record.loading"
                        :un-checked-children="$t('已禁用')"
                        class="flex-shrink-0"
                        size="small"
                        @click="record.status=!record.status"
                    />
                  </a-popconfirm>
                  <a-popconfirm
                      :cancel-text="$t('否')"
                      :ok-text="$t('是')"
                      ok-type="danger"
                      @confirm="deleteRecordById(record.id)"
                  >
                    <template #icon>
                      <question-circle-outlined style="color: red"/>
                    </template>
                    <template #title>
                      <div>是否删除推荐品牌？</div>
                      <a-tag class="my-2" color="red">{{
                          record.brandName
                        }}
                      </a-tag>
                    </template>
                    <a-button class="flex items-center ml-2" danger type="link">
                      <DeleteOutlined/>
                    </a-button>
                  </a-popconfirm>
                  <a-button class="flex items-center" type="link" @click="emit('openModal', record)">
                    <EditOutlined/>
                  </a-button>
                </div>
              </div>
            </template>
            <template #avatar>
              <a-avatar :src="record.logo" size="large"/>
            </template>
          </a-list-item-meta>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>