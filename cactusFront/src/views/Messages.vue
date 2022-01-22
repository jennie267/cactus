<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8">
            <div class="card-header bg-transparent row align-items-center">
                <h1 class=""><h1 class="ni ni-email-83"></h1>  {{user.name}}님의 수신함  </h1>
                <write-letter-modal ref="letterModal" :user="user"></write-letter-modal>
            </div>
        </base-header>

        <div class="container-fluid mt--7">
            <children-table ref="childTable" title="Children Table" :user="user"></children-table>
            <br>
            <div class="row">
                <div class="col">
                    <messages-table ref="msgTable" :user="user" title="Message Table"></messages-table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import MessagesTable from './Tables/MessagesTable'
    import ChildrenTable from './Tables/ChildrenTable'
    import WriteLetterModal from './Modals/WriteLetterModal'


    export default {
        name: 'tables',
        components: {
            MessagesTable
            ,ChildrenTable
            ,WriteLetterModal
        },
        data() {
            return {
                user: this.$store.state.user,
                children: []
            }
        },
        methods: {
            sendUserId: function (sendUserId) {
                // this.$refs.msgTable.$forceUpdate();
                this.$refs.msgTable.receiveMsg(sendUserId, this.user);
            },
            receiveChildren: function (user) {
                console.log('message  receiveChildren Method ', user.type);
                this.children = [];
                if (user.type =='PARENT'){
                    this.$http.get(`/user/children/`+user.userId,  { headers: { Authorization: `Bearer `+user.token } })
                        .then(res => {
                            res.data.users.forEach(child => this.children.push(child));

                        });
                } else if (user.type =='CHILD') {
                    this.$http.get(`/user/parents/`+user.userId,  { headers: { Authorization: `Bearer `+user.token } })
                        .then(res => {
                            res.data.users.forEach(child => this.children.push(child));

                        });
                }

            }, replyMessage: function (revUserId) {
                console.log('[PARENT] Messages   ===>  ', revUserId);
                this.$refs.letterModal.showModal(revUserId);
            }
        },
        created () {
            this.receiveChildren(this.user);


        }, mounted () {
            this.$refs.msgTable.receiveMsg('all', this.user);
            // this.$refs.childTable.receiveChildren(this.user);
            this.$refs.childTable.beforeSetting(this.children);
            this.$refs.letterModal.beforeSetting(this.children);

        }
    };



</script>

<style></style>
