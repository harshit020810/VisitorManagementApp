package com.example.visitormanagementapp.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitormanagementapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisitHolder extends RecyclerView.ViewHolder {

    public TextView timeResponse, visitorNameResponse, visitorCompanyResponse, hostNameResponse, hostDepartmentResponse;
    public ImageView activeImage, stateImage;
    public CircleImageView visitorImage;

    public VisitHolder(@NonNull View itemView) {
        super(itemView);
        timeResponse = itemView.findViewById(R.id.timeResponse);
        visitorNameResponse = itemView.findViewById(R.id.visitorNameResponse);
        visitorCompanyResponse = itemView.findViewById(R.id.visitorCompanyResponse);
        hostNameResponse = itemView.findViewById(R.id.hostNameResponse);
        hostDepartmentResponse = itemView.findViewById(R.id.hostDepartmentResponse);
        activeImage = itemView.findViewById(R.id.activeImage);
        stateImage = itemView.findViewById(R.id.stateImage);
        visitorImage = itemView.findViewById(R.id.visitorImage);
    }
}
