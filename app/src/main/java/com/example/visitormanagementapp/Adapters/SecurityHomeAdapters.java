package com.example.visitormanagementapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitormanagementapp.Interfaces.RoomDaoForVisit;
import com.example.visitormanagementapp.Models.RoomEntityForVisit;
import com.example.visitormanagementapp.R;

import java.util.List;

public class SecurityHomeAdapters extends RecyclerView.Adapter<SecurityHomeAdapters.ViewHolder> {

    private Context context;
    private List<RoomEntityForVisit> list;

    public SecurityHomeAdapters(Context context) {
       this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_for_security_home, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(list!=null){
            RoomEntityForVisit roomEntityForVisit = list.get(position);
            if(roomEntityForVisit.getAppointment().charAt(1) == '1'){
                holder.appointment.setText(roomEntityForVisit.getAppointment() + " AM");
            }else{
                holder.appointment.setText(roomEntityForVisit.getAppointment() + " PM");
            }
            holder.visitorName.setText(roomEntityForVisit.getVisitorName());
            holder.visitorCompany.setText(roomEntityForVisit.getCompanyName());
            holder.hostName.setText(roomEntityForVisit.getHost());
            holder.hostDepartment.setText(roomEntityForVisit.getDepartment());
            if(roomEntityForVisit.isActive()){
                holder.activeImage.setImageResource(R.color.green);
            }else{
                holder.activeImage.setImageResource(R.color.red);
            }

            if(roomEntityForVisit.getState().equals("Processed")){
                holder.stateImage.setImageResource(R.color.yellow);
            }else if(roomEntityForVisit.getState().equals("Approved")){
                holder.stateImage.setImageResource(R.color.green);
            }else{
                holder.stateImage.setImageResource(R.color.red);
            }

        }
    }

    public void setVisitInfo(List<RoomEntityForVisit> roomEntityForVisit){
        list = roomEntityForVisit;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(list !=null){
            return list.size();
        }else{
            return 0;
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView appointment, visitorName, visitorCompany, hostName, hostDepartment;

        public ImageView activeImage, stateImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appointment = itemView.findViewById(R.id.timeResponse);
            visitorName = itemView.findViewById(R.id.visitorNameResponse);
            visitorCompany = itemView.findViewById(R.id.visitorCompanyResponse);
            hostName = itemView.findViewById(R.id.hostNameResponse);
            hostDepartment = itemView.findViewById(R.id.hostDepartmentResponse);
            activeImage = itemView.findViewById(R.id.activeImage);
            stateImage = itemView.findViewById(R.id.stateImage);

        }
    }
}

