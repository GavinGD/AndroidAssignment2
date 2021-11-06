package ca.bcit.assignment2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CaseListAdapter extends ArrayAdapter<Case> {
    private Activity context;
    private List<Case> covidCaseList;

    public CaseListAdapter(Activity context, List<Case> inputCaseList) {
        super(context, R.layout.list_layout, inputCaseList);
        this.context = context;
        this.covidCaseList = inputCaseList;
    }

    public CaseListAdapter(Context context, int resource, List<Case> objects, Activity context1, List<Case> caseList) {
        super(context, resource, objects);
        this.context = context1;
        this.covidCaseList = caseList;
    }

    //******************//USES list_layout.xml; gets each Attribute from COVID Case; setsText for each TextView//*********************
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvReportDate = listViewItem.findViewById(R.id.reportedDate);
        TextView tvHealthAuth = listViewItem.findViewById(R.id.healthAuthority);
        TextView tvLabDiagnosis = listViewItem.findViewById(R.id.labDiagnosis);
        TextView tvAgeGrp = listViewItem.findViewById(R.id.ageGroup);
        TextView tvGender = listViewItem.findViewById(R.id.gender);

        Case covidCase = covidCaseList.get(position);
        tvReportDate.setText(covidCase.getReported_Date());
        tvHealthAuth.setText(covidCase.getHA());
        tvLabDiagnosis.setText(covidCase.getClassification_Reported());
        tvAgeGrp.setText(covidCase.getAge_group());
        tvGender.setText(covidCase.getSex());

        return listViewItem;
    }
}
