const NotificationStore = {
  state: [],
  delay: 4000,
  count: 0,

  // sequential id generator
  IDGen() {
    return this.count++;
  },


  addNotification(notification) {
    notification.id = this.IDGen()
    this.state.push(notification)
  },

  removeNotification(notification) {
    if(this.state.length>0) {
      const index = this.state.indexOf(notification)
      if(index>-1){
        this.state.splice(index, 1)
      }
    }
  },

  getAllNotifications() {
    return [...this.state]
  },

  removeAllNotifications(){
    this.state.splice(0,this.state.length)
    this.count = 0
  }
}

export default NotificationStore
