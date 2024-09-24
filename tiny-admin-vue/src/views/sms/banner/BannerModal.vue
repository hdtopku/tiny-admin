<script lang="ts" setup>
import {Ref, ref} from 'vue';
import {message} from "ant-design-vue";
import ImageUpload from "@/components/ImageUpload.vue";
import {saveOrUpdateBanner} from "@/api/banner.ts";

const open = ref<boolean>(false);
const isUpdate = ref<boolean>(false);
const bannerInfo = ref<any>({});
const rules: Ref<any> = ref({
  bannerName: [
    {required: true, message: '请输入轮播卡片名称', trigger: ['blur', 'change']},
    {min: 1, max: 50, message: '长度在 2 到 30 个字符', trigger: ['blur', 'change']}
  ],
})
const formRef = ref()
const formLoading = ref(false)
const emits = defineEmits(['queryList']);
const handleOk = () => {
  formRef.value.validate().then(() => {
    formLoading.value = true;
    saveOrUpdateBanner(bannerInfo.value).then(() => {
      message.success("操作成功");
      open.value = false
      emits('queryList')
    }).finally(() => {
      formLoading.value = false
    })
  }).catch((err) => {
    console.log('error', err)
  })
}
const activeKey = ref('basicInfo');

const showModal = (banner: any = {}) => {
  if (banner.id) {
    isUpdate.value = true;
    bannerInfo.value = Object.assign({}, banner);
  } else {
    bannerInfo.value = {}
  }
  open.value = true;
}

defineExpose({
  showModal
})

watch(bannerInfo, (newVal) => {
  console.log(newVal)
})
</script>
<template>
  <div>
    <a-modal v-model:open="open" :title="isUpdate? '更新轮播卡片' : '新增轮播卡片'" cancel-text="取消" destroy-on-close
             ok-text="提交"
             @ok="handleOk">
      <template #footer>
        <a-space class="flex justify-end">
          <a-button key="back" @click="open = false">取消</a-button>
          <a-button key="submit" :loading="formLoading" type="primary" @click="handleOk">提交</a-button>
        </a-space>
      </template>
      <a-form v-show="activeKey === 'basicInfo'" ref="formRef" :label-col="{span: 4}" :model="bannerInfo" :rules="rules">
        <a-form-item label="轮播图片" name="logo">
          <div class="flex justify-center">
            <ImageUpload v-model:image-url="bannerInfo.picUrl"></ImageUpload>
            <a-textarea v-model:value="bannerInfo.picUrl"/>
          </div>
        </a-form-item>
        <a-form-item label="卡片名称" name="bannerName">
          <a-input v-model:value="bannerInfo.bannerName" allowClear placeholder="请输入轮播卡片名称"/>
        </a-form-item>
        <a-form-item label="跳转链接" name="linkTo">
          <a-textarea v-model:value="bannerInfo.linkTo" :rows="3" allowClear placeholder="请输入跳转链接，支持http/https协议"/>
        </a-form-item>
        <div class="grid grid-cols-2 justify-center gap-4">
          <a-form-item :label-col="{span: 8}" label="展示平台" name="platform">
            <a-select :default-value="1"  v-model:value="bannerInfo.platform">
              <a-select-option :value="1">电脑端</a-select-option>
              <a-select-option :value="2">移动端</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item :label-col="{span: 10}" label="排序" name="sort">
            <a-input-number default-value="9999" v-model:value="bannerInfo.sort" :max="99999999" :min="0"/>
          </a-form-item>
        </div>
        <a-form-item label="备注" name="remark">
          <a-textarea v-model:value="bannerInfo.remark" :rows="3" allowClear placeholder="备注信息仅在后台管理显示，不会对用户显示"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

