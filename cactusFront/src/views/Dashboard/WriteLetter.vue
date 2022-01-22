<template>
    <div class="card shadow text-center">
        <div class="card-header box5Header">
            <div class="row">
                <h3 v-if="revUser.userId == null && user.type == 'PARENT'" class="mb-0 todaySchHeaderStr"><h1 class="ni ni-email-83 todaySchHeaderStr"></h1> 자녀목록에서 수신자를 선택해주세요.</h3>
                <h3 v-if="revUser.userId == null && user.type == 'CHILD'" class="mb-0 todaySchHeaderStr"><h1 class="ni ni-email-83 todaySchHeaderStr"></h1> 부모목록에서 수신자를 선택해주세요.</h3>
                <h3 v-if="revUser.userId != null"  class="mb-0 todaySchHeaderStr"><h1 class="ni ni-email-83 todaySchHeaderStr"></h1> {{revUser.name}}님께 쪽지 보내기</h3>
            </div>
        </div>

            <form>
                <textarea class="form-control" style="width: 100%;height: 200px;" v-model="message" placeholder="내용을 입력하세요."></textarea>
            </form>
            <br>
            <div class="text-right">
                <base-button type="primary" @click.prevent="sendMessage(revUser.userId)">전송</base-button>
                <base-button type="neutral" class="ml-auto" @click="modals.modal1 = false" >취소
                </base-button>
            </div>
    </div>
</template>
<script>
    let msg = {contents:"", receivedUserId:0, sendUserId:0,isLike:"N"};
    export default {
        components: {
        },
        props: {
            user: Object,
            revUser: Object
        },
        data() {
            return {
                message: ''
            };
        },
        methods: {
            sendMessage: function(revUserId){
                if (this.revUser.userId == null){
                    this.$swal({
                        type: 'warning',
                        title: '받는 사람을 선택해주세요.'
                    });
                } else {
                    msg.contents = this.message;
                    msg.sendUserId = this.user.userId;
                    msg.receivedUserId = revUserId;

                    console.log('[Message Send]  received : ' + msg.receivedUserId +' sendId : ' + msg.sendUserId + ' checked : '+ revUserId );
                    this.$http.post(`/message/`, msg,
                        {
                            headers: {
                                Authorization: `Bearer ${this.user.token}`
                                ,'Content-Type':'application/json'
                            }
                        })
                        .then(res => {
                            if (res == null) {
                                this.$swal({
                                    type: 'warning',
                                    title: '전송 실패했습니다.'
                                });
                            } else {
                                this.message = "";
                                msg.contents = "";
                                this.$swal({
                                    type: 'success',
                                    title: '전송 성공했습니다.'
                                });
                            }
                        });

                }

            }
        }
    };

</script>
<style>
</style>
