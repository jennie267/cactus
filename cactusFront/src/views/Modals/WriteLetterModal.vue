<template>
    <!-- Modals -->
    <div class="col text-right">
        <base-button type="default" class="btn btn-primary btn-md" @click="showModal(null)">
            편지쓰기
        </base-button>

        <modal :show.sync="modals.modal1">
            <h2 class="modal-title text-left" id="modal-title-default"><h1 class="ni ni-email-83"></h1>  쪽지보내기</h2>
            <br>
            <div class="row"  >
                <div v-for="child in children" :key="child.userId"  class="custom-control custom-checkbox text-left" style="margin-right: 6%;" >
                    <input class="custom-control-input" type="checkbox" :id="child.userId" :value="child.userId" v-model="checkedNames">
                    <label class="custom-control-label" :for="child.userId">{{child.name}}</label>
                </div>
            </div>

            <br>
            <form>
                <textarea class="form-control" style="width: 100%;height: 200px;" v-model="message" placeholder="내용을 입력하세요."></textarea>
            </form>
            <br>
            <div class="text-right">
                <base-button type="primary" @click.prevent="sendMessage()">전송</base-button>
                <base-button type="neutral" class="ml-auto" @click="modals.modal1 = false" >취소
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
        props: {
            user: Object
        },
        data() {
            return {
                message: '',
                checkedNames: [],
                /* children:this.revChildren,*/
                /*user: this.$store.state.user,*/
                modals: {
                    modal1: false
                },
                children: []
            };
        },
        methods: {
            beforeSetting: function(revchildren){
                console.log('ChildrenTable  beforeSetting [Method] ',revchildren);
                this.children = revchildren;
            },
            sendMessage: function(){
                if (this.checkedNames.length == 0){
                    this.$swal({
                        type: 'warning',
                        title: '받는 사람을 선택해주세요.'
                    });
                } else {
                    this.checkedNames.forEach(checkedId => {
                        let msg = {
                            contents: this.message,
                            receivedUserId: checkedId,
                            sendUserId: this.user.userId,
                            isLike: "N",
                        };

                        this.$http.post(`/message/`, msg,
                            {
                                headers: {
                                    Authorization: `Bearer ${this.user.token}`
                                    ,'Content-Type':'application/json'
                                }
                            })
                            .then(res => {
                                if (res.status===2000){
                                    this.modals.modal1 = false;
                                    this.$swal({
                                        type: 'success',
                                        title: '전송 성공했습니다.'
                                    });
                                }
                                else{
                                    this.$swal({
                                        type: 'warning',
                                        title: '전송 실패했습니다.'
                                    });
                                }
                            });
                    });
                }
            }
            ,showModal:function (revUserId) {
                this.checkedNames = [];
                this.message = '';
                this.modals.modal1 = true;
                if (revUserId != null) this.checkedNames.push(revUserId);
            }
        }
    };

</script>
<style>
</style>
