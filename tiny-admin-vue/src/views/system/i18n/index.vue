<script setup lang="ts">
import Search from "@/components/Search.vue"
import {
  deleteRawContent,
  deleteTranslation,
  getI18nPage,
  saveOrUpdateRawContent,
  saveOrUpdateTranslation
} from "@/api/system/sysI18n.ts";
import {DeleteOutlined, DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {message} from "ant-design-vue";

const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0,
})
const searchForm = ref({
  keyword: '',
  status: true,
  pageNum: pagination.value.current,
  pageSize: pagination.value.pageSize,
})
const queryList = () => {
  getI18nPage(searchForm.value).then((res: any) => {
    dataSource.value = res.records
    pagination.value = {
      current: res.current,
      pageSize: res.size,
      total: res.total,
    }
  })
}
queryList()
const columns = [
  {
    title: '待翻译文本',
    dataIndex: 'rawContent',
    width: 100,
  },
  {
    title: '已翻译',
    dataIndex: 'translationList',
    width: 200,
  },
  {
    title: '操作',
    key: 'action',
    width: 100,
  }
]
const dataSource = ref([])
const editContent = ref('')
const languageList = ['CN', 'EN', 'JP', 'KR', 'TW']
const open = ref(false)
const form = ref({
  rawContent: '',
})
const rules = {
  rawContent: [
    {required: true, message: '请输入待翻译文本', trigger: ['blur', 'change']},
  ],
}
const handleSubmit = () => {
  saveOrUpdateRawContent({rawContent: form.value.rawContent}).then(() => {
    message.success('操作成功')
    queryList()
  })
}
const handleDeleteRawContent = (id: string) => {
  deleteRawContent(id).then(() => {
    message.success('删除成功')
    queryList()
  })
}
const handleDeleteTranslation = (id: string) => {
  deleteTranslation(id).then(() => {
    message.success('删除成功')
    queryList()
  })
}

</script>

<template>
  <div>
    <Search :search-form="searchForm"/>
    <a-table :columns="columns" :data-source="dataSource" :pagination="pagination" row-key="id">
      <template #bodyCell="{ record, index, column }">
        <template v-if="column.dataIndex === 'rawContent'">
          <div class="flex items-start gap-1">
            <a-button class="group" danger shape="circle" size="small" type="primary">
              <template #icon>
                <a-popconfirm :cancel-text="$t('否')"
                              :ok-text="$t('是')" @confirm="handleDeleteRawContent(record.id)">
                  <template #title>
                    <div>{{ $t('是否删除该文本？') }}</div>
                    <a-tag class="my-2" color="red"
                    >{{ record.rawContent }}
                    </a-tag
                    >
                  </template>

                  <div class="flex items-center justify-center group-hover:scale-110 duration-300">
                    <DeleteOutlined/>
                  </div>
                </a-popconfirm>
              </template>
            </a-button>

            <a-typography-paragraph v-model:content="record.rawContent" :ellipsis="{ rows: 2, expandable: true, symbol: 'more' }"
                                    copyable>
            </a-typography-paragraph>
          </div>
        </template>
        <template v-if=" column.dataIndex===
          'translationList'">
          <div v-for="(item, idx) in record.translationList" :key="idx" class="flex items-center space-y-1">
            <a-tag>{{ item.languageCode }}</a-tag>
            <div class="flex items-center justify-start">
              <a-typography-paragraph :content="item.translatedContent"
                                      :editable="{
                tooltip: false,
                onStart: () => {
                  editContent = item.translatedContent
                },
                onChange: (content: any) => {
                  editContent = content
                },
                onEnd: () => {
                  saveOrUpdateTranslation({...item, translatedContent: editContent}).then(() => {
                    queryList()
                  })
                },
                }" :ellipsis="{ rows: 2, expandable: true, symbol: 'more' }" copyable>
              </a-typography-paragraph>

              <a-popconfirm :cancel-text="$t('否')" :ok-text="$t('是')" @confirm="handleDeleteTranslation(item.id)">
                <template #title>
                  <div>{{ $t('是否删除该翻译？') }}</div>
                  <a-tag>{{ item.languageCode }}</a-tag>
                  <a-tag class="my-2" color="red"
                  >{{ item.translatedContent }}
                  </a-tag
                  >
                </template>
                <a-button class="group" danger shape="circle" size="small" type="link">
                  <template #icon>
                    <div class="flex items-center justify-center group-hover:scale-110 duration-300">
                      <DeleteOutlined/>
                    </div>
                  </template>
                </a-button>
              </a-popconfirm>
            </div>
          </div>
          <div v-for="(item, idx) in languageList.filter(i=>!record.translationList.some(j=>j.languageCode===i))"
               :key="idx"
               class="flex items-center justify-start gap-1">
            <a-tag>{{ item }}</a-tag>
            <a-typography-paragraph :editable="{
                tooltip: false,
                onChange: (content: any) => {
                  editContent = content
                },
                onEnd: () => {
                  saveOrUpdateTranslation({rawId: record.id, languageCode: item, translatedContent: editContent}).then(() => {
                    queryList()
                  })
                },
                }" content="">
            </a-typography-paragraph>
          </div>
        </template>
        <template v-if="column.dataIndex ==='status'">
          <a-switch :checked="record.status" @change="() => { record.status =!record.status; }"></a-switch>
        </template>
        <template v-if="column.key === 'action'">
          <div class="grid grid-cols-2 items-center justify-center">
            <a-popconfirm
                :cancel-text="$t('否')"
                :ok-text="$t('是')"
                :title="
                  record.status
                    ? $t('是否禁用该用户？')
                    : $t(' 是否启用该用户？')
                "
                @confirm="
                  () => {
                    confirmChangeStatus(record)
                  }
                "
            >
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
                  @click="
                    () => {
                      changeStatus(record)
                    }
                  "
              />
            </a-popconfirm>
            <a-dropdown>
              <a-button class="flex items-center" size="small" type="link"
              >{{ $t('操作') }}
                <DownOutlined/>
              </a-button>
              <template #overlay>
                <a-menu class="text-center">
                  <a-menu-item>
                    <a-button
                        type="link"
                        @click="() => saveOrUpdateUserInfo(record, true)"
                    >编辑文本
                    </a-button
                    >
                  </a-menu-item>
                  <a-menu-item>
                    <a-button
                        class="text-orange-300"
                        type="link"
                        @click="handleChangePassword(record)"
                    >{{ $t('重置密码') }}
                    </a-button
                    >
                  </a-menu-item>
                  <a-menu-item>
                    <a-button type="link">{{ $t('更换头像') }}</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm
                        :cancel-text="$t('否')"
                        :ok-text="$t('是')"
                        ok-type="danger"
                        @confirm="
                          () => {
                            handleDeleteUser(record.key)
                          }
                        "
                    >
                      <template #icon>
                        <question-circle-outlined style="color: red"/>
                      </template>
                      <template #title>
                        <div>{{ $t('是否删除用户？') }}</div>
                        <a-tag class="my-2" color="red"
                        >{{ record.username }}_{{ record.nickname }}
                        </a-tag
                        >
                      </template>
                      <a-button danger type="link">{{
                          $t('删除用户')
                        }}
                      </a-button>
                    </a-popconfirm>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
        </template>
      </template>
    </a-table>
    <a-modal :open="open">
      <template #title>新增待翻译文本</template>
      <a-form :model="form" :rules="rules">
        <a-form-item label="待翻译文本" name="rawContent">
          <a-textarea v-model:value="form.rawContent" :rows="4" allow-clear/>
        </a-form-item>
      </a-form>
      <template #footer>
        <a-button @click="open = false">{{ $t('取消') }}</a-button>
        <a-button type="primary"
                  @click="handleSubmit">
          {{ $t('提交') }}
        </a-button>
      </template>
    </a-modal>
  </div>
</template>

<style scoped>
::v-deep(.ant-typography) {
  margin: 0;
}
</style>