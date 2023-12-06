package beans;

public class new_msg {

    private final String name;
    private final String device_name;
    private final String description;
    private final String create_date;
    private final String fixed_date;
    private final int event_id;

    public new_msg(String name, String device_name, String description, String create_date, String fixed_date, int event_id) {
        this.name = name;
        this.device_name = device_name;
        this.description = description;
        this.create_date = create_date;
        this.fixed_date = fixed_date;
        this.event_id = event_id;
    }

    public String getName() {
        return name;
    }

    public String getDevice_name() {
        return device_name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getFixed_date() {
        return fixed_date;
    }

    public int getEvent_id() {
        return event_id;
    }

    @Override
    public String toString() {
        return "new_msg{" +
                "name='" + name + '\'' +
                ", device_name='" + device_name + '\'' +
                ", description='" + description + '\'' +
                ", create_date='" + create_date + '\'' +
                ", fixed_date='" + fixed_date + '\'' +
                ", event_id=" + event_id +
                '}';
    }
}
