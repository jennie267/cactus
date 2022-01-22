<template >
    <div>
        <modal :show.sync="modals.modal1">
            <div class="row">
                <img src="img/brand/white.png" style="width:25%;">
                <div id="container" class="card-header bg-transparent row align-items-center">
                    <br>
                    <h2 slot="header" class="modal-title" id="modal-title-default" align="center"><i class="ni ni-badge"></i>   부모 회원가입</h2><br>
                </div>
                <div class="col-lg-12" style="text-align:left">
                    <base-input alternative=""
                                label="아이디"
                                placeholder="Id"
                                input-classes="form-control-alternative"
                                v-model="user.id"
                                ref="id"
                    />
                    <input style="float:right;" type="button" class="btn btn-sm btn-outline-primary" value="중복 확인" @click="idCheck()">
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12" style="text-align:left">
                    <base-input alternative=""
                                label="비밀번호"
                                placeholder="Password"
                                input-classes="form-control-alternative"
                                v-model="user.password"
                                type="password"
                                ref="password"
                    />
                    <small>{{ pwValidation }}</small>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12" style="text-align:left">
                    <base-input alternative=""
                                label="비밀번호 확인"
                                placeholder="Password"
                                input-classes="form-control-alternative"
                                v-model="user.passwordchk"
                                type="password"
                                ref="passwordchk"
                    />
                    <small>{{ pwCheck }}</small>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12" style="text-align:left">
                    <base-input alternative=""
                                label="이름"
                                placeholder="Username"
                                input-classes="form-control-alternative"
                                v-model="user.name"
                    />
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12" style="text-align:left">
                    <base-input label="성별">
                        <div class="custom-control custom-radio mb-3">
                            <input name="custom-radio-1" class="custom-control-input" id="customRadio1" type="radio" v-model="user.gender" value="여">
                            <label class="custom-control-label" for="customRadio1"><span>여성</span></label>
                        </div>
                        <div class="custom-control custom-radio mb-3">
                            <input name="custom-radio-1" class="custom-control-input" id="customRadio2" type="radio" v-model="user.gender" value="남">
                            <label class="custom-control-label" for="customRadio2"><span>남성</span></label>
                        </div>
                    </base-input>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-lg-12" style="text-align:left">
                    <base-input alternative=""
                                label="이메일"
                                placeholder="mail@example.com"
                                input-classes="form-control-alternative"
                                v-model="user.email"
                    />
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12" style="text-align:left">
                    <base-input alternative=""
                                label="전화번호"
                                placeholder="Phone number"
                                input-classes="form-control-alternative"
                                v-model="user.tel"
                    />
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12" style="text-align:left">
                    <base-input label="생일">
                        <div addon-left-icon="ni ni-calendar-grid-58">
                        </div>
                        <flat-pickr slot-scope="{focus, blur}"
                                    @on-open="focus"
                                    @on-close="blur"
                                    :config="{allowInput: true}"
                                    class="form-control datepicker"
                                    placeholder="birthday"
                                    v-model="user.birthday">
                        </flat-pickr>
                    </base-input>
                </div>
            </div>
            <div class="pl-lg-4">
                <div
                        ref="searchWindow"
                        :style="searchWindow"
                        style="border:1px solid;width:500px;margin:5px 0;position:relative"
                >
                    <img
                            src="//t1.daumcdn.net/postcode/resource/images/close.png"
                            id="btnFoldWrap"
                            style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1"
                            @click="searchWindow.display = 'none'"
                            alt="close"
                    >
                </div>
            </div>
            <div class="row">
                <div class="col-lg-8" style="text-align:left">
                    <base-input alternative=""
                                label="주소"
                                placeholder="Address"
                                input-classes="form-control-alternative"
                                v-model="user.addrMain"
                    />
                </div>
                <div class="col-lg-4" style="text-align:left">
                    <base-input alternative=""
                                label="우편번호"
                                placeholder="zipCode"
                                input-classes="form-control-alternative"
                                v-model="user.zipCode"
                    />
                    <input type="button" style="float:right;" class="btn btn-sm btn-outline-primary" value="우편번호 찾기" @click="execDaumPostcode">
                    <post-code-modal></post-code-modal>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12" style="text-align:left">
                    <base-input alternative=""
                                label="상세 주소"
                                placeholder="Address"
                                input-classes="form-control-alternative"
                                v-model="user.addrSub"
                                ref="addrSub"
                    />
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12" style="text-align:center">
                    <input type="button" class="btn btn btn-primary cactusBasicBtn" @click="doOidSignup" value="가입하기">
                    <base-button type="button" class="btn btn btn-outline-primary cactusCancleBtn" @click="click()">나가기</base-button>
                </div>
            </div>
        </modal>
    </div>
</template>
<script src="/assets/vendor/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script>
    import Vue from 'vue';
    import Modal from "@/components/Modal.vue";
    import flatPickr from 'vue-flatpickr-component';
    import 'flatpickr/dist/flatpickr.css';
    import VueSweetalert2 from 'vue-sweetalert2';
    import 'sweetalert2/dist/sweetalert2.min.css';
    import axios from 'axios'
    Vue.use(VueSweetalert2);
    Vue.prototype.$http=axios


    let idValid = false;
    export default {
        methods: {
            openModal(){
                this.modals.modal1 = true;
            },
            click(){
                this.modals.modal1=false;
                Object.assign(this.$data, this.$options.data());
            },
            close() {
                this.$emit('close');
            }
            ,execDaumPostcode() {
                const currentScroll = Math.max(
                    document.body.scrollTop,
                    document.documentElement.scrollTop,
                );
                new daum.Postcode({
                    onComplete: (data) => {
                        if (data.userSelectedType === 'R') {
                            this.user.addrMain = data.roadAddress;
                        } else {
                            this.user.addrMain = data.jibunAddress;
                        }

                        if (data.userSelectedType === 'R') {
                            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                                this.user.addrSub += data.bname;
                            }

                            if (data.buildingName !== '' && data.apartment === 'Y') {
                                this.user.addrSub +=
                                    this.user.addrSub !== ''
                                        ? `, ${data.buildingName}`
                                        : data.buildingName;
                            }

                            if (this.user.addrSub !== '') {
                                this.user.addrSub = ` (${this.user.addrSub})`;
                            }
                        } else {
                            this.user.addrSub = '';
                        }

                        this.user.zipCode = data.zonecode;

                        //this.$refs.extraAddress.focus();

                        this.searchWindow.display = 'none';
                        document.body.scrollTop = currentScroll;
                        this.$router.go(-1);
                    },
                    onResize: (size) => {
                        this.searchWindow.height = `${size.height}px`;
                    },
                    width: '100%',
                    height: '100%',
                }).embed(this.$refs.searchWindow);

                this.searchWindow.display = 'block';
            },
            doOidSignup() {
                if (!this.idValid){
                    this.$swal({
                        type: 'warning',
                        title: '아이디 중복체크 해주세요.'
                    });
                } else if(this.user.password.length <8){
                    this.$swal({
                        type: 'warning',
                        title: '비밀번호는 8자리 이상이어야 합니다.'
                    });
                } else if(this.user.password != this.user.passwordchk){
                    this.$swal({
                        type: 'warning',
                        title: '비밀번호와 비밀번호확인이 같지 않습니다.'
                    });
                }else if (this.user.id==null||this.user.id=="") {
                    this.$swal({
                        type: 'warning',
                        title: '아이디를 입력하여 주십시오.'
                    });
                }else if (this.user.gender==null||this.user.gender=="") {
                    this.$swal({
                        type: 'warning',
                        title: '성별을  입력하여 주십시오.'
                    });
                }else if (this.user.email==null||this.user.email=="") {
                    this.$swal({
                        type: 'warning',
                        title: '이메일을  입력하여 주십시오.'
                    });
                }else if (this.user.password==null||this.user.password=="") {
                    this.$swal({
                        type: 'warning',
                        title: '비밀번호를  입력하여 주십시오.'
                    });
                }else if (this.user.passwordchk==null||this.user.passwordchk=="") {
                    this.$swal({
                        type: 'warning',
                        title: '비밀번호를  입력하여 주십시오.'
                    });
                }else if (this.user.name==null||this.user.name=="") {
                    this.$swal({
                        type: 'warning',
                        title: '이름을 입력하여 주십시오.'
                    });
                }else{
                    this.$http.post(`/user/signup/`, this.user,
                        {
                            headers: {
                                Authorization: `Bearer ${this.user.token}`
                                ,'Content-Type':'application/json'
                            },
                        })
                        .then(res => {
                            Vue.swal('가입을 환영합니다!');
                            this.modals.modal1=false;
                        });
                }
            },
            idCheck() {
                this.$http.get(`/user/idcheck/`+this.user.id)
                    .then(res => {
                        console.log(res.data);
                        if(res.data==1) {
                            this.idValid = false;
                            this.$swal({
                                type: 'warning',
                                title: '동일한 아이디가 존재합니다.'
                            });
                        } else if(res.data==0) {
                            this.idValid = true;
                            this.$swal({
                                type: 'success',
                                title: '아이디를 사용하실 수 있습니다.'
                            });
                        }
                    });
            }
        },
        computed: {
            pwValidation: function() {
                if(!this.user.password.length==0) {
                    return this.user.password.length > 8 ? `` : `[주의] 비밀번호는 8자 이상으로 작성해주세요.`;
                }
            },
            pwCheck : function() {
                if(!this.user.passwordchk.length==0) {
                    if(this.user.password != this.user.passwordchk) {
                        return `[주의] 비밀번호가 동일하지 않습니다.`;
                    }
                }
            }
        },
        components: {
            Modal
            ,flatPickr
        },
        data() {
            return {
                checked:[],
                user: {
                    userId:'',
                    id:'',
                    password:'',
                    passwordchk:'',
                    name: '',
                    gender:[],
                    email: '',
                    addrMain: '',
                    addrSub: '',
                    zipCode: '',
                    birthday:'',
                    tel:'',
                    type:'PARENT'
                },
                searchWindow: {
                    display: 'none',
                    height: '300px',
                },
                modals: {
                    modal1:false
                }
            }
        }
    };
</script>
<style type="text/css">
    #container {
        display:table;
        width:125%;
        height:100%;
        text-align: center;
    }

    #container span {
        display: table-cell;
        vertical-align: middle;
    }


</style>
