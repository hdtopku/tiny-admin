<script lang="ts" setup>
import {message, Pagination} from "ant-design-vue";
import {DeleteOutlined, EditOutlined, QuestionCircleOutlined,} from '@ant-design/icons-vue'
import {deleteBannerById, saveOrUpdateBanner} from "@/api/sms/banner.ts";
import {t} from "@/utils/i18n.ts";

const props: any = defineProps({
  dataSource: Array,
  pagination: Pagination,
  isLoading: Boolean
})
const emit = defineEmits(['openModal', 'queryList'])
const confirmChangeStatus = (record: any) => {
  record.loading = true
  const {loading, createTime, updateTime, ...rest} = record
  rest.status = !rest.status
  saveOrUpdateBanner(rest).then(() => {
    record.status = rest.status
    message.success(t('操作成功'))
  }).finally(() => {
    record.loading = false
  })
}
const openModal = (record: any) => {
  emit('openModal', record)
}
const handlePageChange = (pageNum: number, pageSize: number) => {
  console.log(pageNum, pageSize)
  emit('queryList', {pageNum, pageSize})
}
</script>

<template>
  <div>
    <a-list :data-source="props.dataSource" :loading="props.isLoading"
            :pagination="{...props.pagination, onChange: handlePageChange}"
            item-layout="horizontal">
      <template #renderItem="{ item:record }">
        <a-list-item>
          <a-card>
            <template #cover>
              <div class="bg-gray-100 dark:bg-gray-800 h-48">
                <img :src="record.picUrl" alt=""/>
              </div>
            </template>
            <a-card-meta :title="record.bannerName">
              <template #description>
                {{ record.remark }}
                <div class="mt-2">
                  <a-tag>平台</a-tag>
                  {{ record.platform === 1 ? $t('电脑端') : $t('移动端') }}
                  <a-divider type="vertical"/>
                  <a-tag>排序</a-tag>
                  {{ record.sort }}
                </div>
              </template>
            </a-card-meta>
            <template #actions>
              <a-popconfirm
                  :cancel-text="$t('否')"
                  :ok-text="$t('是')"
                  :title="
                record.status ? $t('是否禁用该轮播卡片？') : $t(' 是否启用该轮播卡片？')
              "
                  @confirm="confirmChangeStatus(record)"
              >
                <template #icon>
                  <QuestionCircleOutlined style="color: red"/>
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
              <a-popconfirm :cancel-text="$t('否')" :ok-text="$t('是')" ok-type="danger"
                            @confirm="deleteBannerById(record.id)">
                <template #icon>
                  <QuestionCircleOutlined style="color: red"/>
                </template>
                <template #title>
                  <div>{{ $t('是否删除该轮播卡片？') }}</div>
                  <a-tag class="my-2" color="red">{{
                      record.bannerName
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
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>