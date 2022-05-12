<template>
  <div class="bodyy">
    <div class="everything">
      <center>
        <p class="heading">Congratulations</p>
        <div class="card" style="width: 40rem">
          <div class="card-body">
            <h5 class="card-title">Your seat has been booked✅</h5>
            <router-view></router-view>
            <button @click="exportToPDF" class="p">Download the Ticket</button>
            <div ref="document" class="doc">
              <center class="ticket">
                <h2 class="p">Ticket for the EVENT</h2>
                <p class="p">Registered user id:{{ user_Id }}</p>
                <p class="p">Registered first name:{{ fnam }}</p>
                <p class="p">Registered last name:{{ lnam }}</p>
                <p class="p">Date:{{ date }}</p>
                <p class="p">Event Title:{{ title }}</p>
                <p class="p">Event Id:{{ eventId }}</p>
              </center>
            </div>
          </div>
        </div>
      </center>
    </div>
  </div>
</template>

<script>
import html2pdf from "html2pdf.js";

export default {
  data() {
    return {
      eventId: 0,
      user_Id: 0,
      title: 0,
      date: 0,
      fnam: 0,
      lnam: 0,
    };
  },
  created() {
    const vm = this;

    vm.eventId = vm.$route.params.eventId;
    vm.user_Id = vm.$route.params.user_Id;
    vm.date = vm.$route.params.date;
    vm.title = vm.$route.params.title;
    vm.fnam = vm.$route.params.fnam;
    vm.lnam = vm.$route.params.lnam;
  },
  methods: {
    exportToPDF() {
      html2pdf(this.$refs.document, {
        margin: 1,
        filename: "ticket.pdf",

        html2canvas: { dpi: 192, letterRendering: true },
        jsPDF: { unit: "in", format: "letter", orientation: "landscape" },
      });
    },
  },
};
</script>


<style scoped>
.p {
  margin-bottom: 15px;
}
.bodyy {
  width: 100%;
  height: 100%;
  background-color: rgb(33, 37, 41);
}
.everything {
  padding-top: 60px;
}
.heading {
  font-family: "Montserrat", sans-serif;
  font-size: 32px;
  font-weight: 400;
  color: rgb(255, 255, 255);
  padding-bottom: 30px;
}
</style>