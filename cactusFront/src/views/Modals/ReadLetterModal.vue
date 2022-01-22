<template>
    <!-- Modals -->
        <div class="col text-right">

            <modal :show.sync="modals.modal1">
                <h2 slot="header" class="modal-title" id="modal-title-default">일자 : 2019-11-24</h2>
                <h2 slot="header" class="modal-title" id="modal-title-default1">이름 : 이지수</h2>
                <div class="ni ni-favourite-28"></div>
                <div class="text-left">
                    엄마 미안해
                </div>
                <div class="text-right">
                    <base-button type="primary" >답장</base-button>
                    <base-button type="warning" >삭제</base-button>
                    <base-button type="link" class="ml-auto" @click="modals.modal1 = false">취소
                    </base-button>
                </div>
            </modal>

        </div>
</template>
<script>
import Modal from "@/components/Modal.vue";
export default {
  components: {
    Modal
  },
  data() {
    return {
        message: [],
      modals: {
          modal1: false
      }
    };
  }
};

</script>
<style>
</style>
