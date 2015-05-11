package cn.edu.sdu.online.utils;

import cn.edu.sdu.online.newcurriculum.R;


public class Main_Curriculum_tv_Id {
	private int[] id = { R.id.main_curriculum00, R.id.main_curriculum01,
			R.id.main_curriculum02, R.id.main_curriculum03,
			R.id.main_curriculum04, R.id.main_curriculum05,R.id.main_curriculum06,
			R.id.main_curriculum10, R.id.main_curriculum11,
			R.id.main_curriculum12, R.id.main_curriculum13,
			R.id.main_curriculum14, R.id.main_curriculum15,R.id.main_curriculum16,
			R.id.main_curriculum20, R.id.main_curriculum21,
			R.id.main_curriculum22, R.id.main_curriculum23,
			R.id.main_curriculum24, R.id.main_curriculum25,R.id.main_curriculum26,
			R.id.main_curriculum30, R.id.main_curriculum31,
			R.id.main_curriculum32, R.id.main_curriculum33,
			R.id.main_curriculum34, R.id.main_curriculum35,R.id.main_curriculum36,
			R.id.main_curriculum40, R.id.main_curriculum41,
			R.id.main_curriculum42, R.id.main_curriculum43,
			R.id.main_curriculum44, R.id.main_curriculum45,R.id.main_curriculum46
			};

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
	}

	public int getIdbyPosition(int x, int y) {
		return id[x * 6 + y + 1];
	}

	public int[] getIdbyCol(int col) {
		int length = id.length;
		switch (col) {
		case 0:
			int[] id_group0 = new int[6];
			for (int i = 0; i < length; i = i + 6) {
				id_group0[i] = id[i];
			}
			return id_group0;
		case 1:
			int[] id_group1 = new int[6];
			for (int i = 1; i < 6; i = i + 6) {
				id_group1[i] = id[i];
			}
			return id_group1;
		case 2:
			int[] id_group2 = new int[6];
			for (int i = 2; i < 6; i = i + 6) {
				id_group2[i] = id[i];
			}
			return id_group2;
		case 3:
			int[] id_group3 = new int[6];
			for (int i = 3; i < 6; i = i + 6) {
				id_group3[i] = id[i];
			}
			return id_group3;
		case 4:
			int[] id_group4 = new int[6];
			for (int i = 4; i < 6; i = i + 6) {
				id_group4[i] = id[i];
			}
			return id_group4;
		case 5:
			int[] id_group5 = new int[6];
			for (int i = 5; i < 6; i = i + 6) {
				id_group5[i] = id[i];
			}
			return id_group5;
		}
		return null;
	}

	public int[] getIdbyRow(int row) {
		switch (row) {
		case 0:
			int[] id_group0 = new int[6];
			for (int i = 0; i < 6; i++) {
				id_group0[i] = id[i];
			}
			return id_group0;
		case 1:
			int[] id_group1 = new int[6];
			for (int i = 0; i < 6; i++) {
				id_group1[i] = id[i + 6];
			}
			return id_group1;
		case 2:
			int[] id_group2 = new int[6];
			for (int i = 0; i < 6; i++) {
				id_group2[i] = id[i + 12];
			}
			return id_group2;
		case 3:
			int[] id_group3 = new int[6];
			for (int i = 0; i < 6; i++) {
				id_group3[i] = id[i + 18];
			}
			return id_group3;
		case 4:
			int[] id_group4 = new int[6];
			for (int i = 0; i < 6; i++) {
				id_group4[i] = id[i + 24];
			}
			return id_group4;
		case 5:
			int[] id_group5 = new int[6];
			for (int i = 0; i < 6; i++) {
				id_group5[i] = id[i + 30];
			}
			return id_group5;
		}
		return null;
	}
}
