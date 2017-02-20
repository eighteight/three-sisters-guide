package ru.scriptum.view.handler.gmap;

import java.util.ArrayList;
import java.util.List;

import ru.scriptum.data.impl.TemplateElement;
import ru.scriptum.model.domaindata.GmapPlace;
import ru.scriptum.model.properties.IProperty;
import ru.scriptum.view.handler.TemplateBean;
import ru.scriptum.view.handler.TemplateListHandler;

public class GmapHandler extends TemplateListHandler {

	private Long currentId;

	private List<GmapPlace> points;

	public Long getCurrentId() {
		return currentId;
	}

	public GmapPlace getCurrentPlace() {
		for (GmapPlace point : points) {
			if (point.getId().equals(currentId))
				return point;
		}
		return points.get(0);
	}

	public List<GmapPlace> getPoints() {
		if (points == null)
			initData();
		return points;
	}

	private void initData() {
		setTemplateType("projectTemplate");
		points = new ArrayList<GmapPlace>();
//		for (TemplateBean templateBean : beanList) {
//			for (TemplateElement templateElement : templateBean.getChildren()) {
//				IProperty[] props = templateElement.getProperties();
//				points.add(new GmapPlace(templateBean.getId(),
//						"/icons/12thnight-matrix.jpg", props[0].getValue(),
//						props[1].getValue(), Integer.parseInt(props[2]
//								.getValue()), templateBean.getName()));
//			}
//
//		}
		if (points.size() == 0) {
			points.add(new GmapPlace ( new Long(1),"/images/blue-folder-closed.png", "42.694777", "23.327488" , 1, "Двенадцатая Ночь, Роберт Стуруа"));
		}
		// points.add(new GmapPlace ("12thnight", "/icons/12thnight-matrix.jpg",
		// "42.694777", "23.327488" , 1,
		// "Двенадцатая Ночь, Роберт Стуруа"));
		// points.add(new GmapPlace ("venus", "/icons/venus-matrix.jpg",
		// "41.298928","-72.934263" , 1,
		// "Венера, Джесси Хилл"));
		// points.add(new GmapPlace ("demon", "/icons/demon-matrix.jpg",
		// "55.74999","37.619977" , 1,
		// "Демон. Вид сверху, Дмитрий Крымов"));
		// points.add(new GmapPlace ("dj", "/icons/dj-matrix.jpg",
		// "59.930143","30.322046" , 1,
		// "Дон Жуан, Александр Морфов"));
		// points.add(new GmapPlace ("dream", "/icons/dream-matrix.jpg",
		// "55.74999","37.619977" , 1,
		// "Сон в летнюю ночь, Мирзоев"));
		// points.add(new GmapPlace ("gg", "/icons/gg-matrix.jpg",
		// "55.74999","37.619977" , 1,
		// "Господа Головлевы, Кирилл Серебренников"));
		// points.add(new GmapPlace ("ki", "/icons/ki-matrix.jpg",
		// "55.74999","37.619977", 1,
		// "КИ из Преступления, Кама Гинкас"));
		// points.add(new GmapPlace ("korol", "/icons/korol-matrix.jpg",
		// "55.74999","37.619977" , 1,
		// "Король умирает, Толстый Болгарин"));
		// points.add(new GmapPlace ("nekroshus", "/icons/nekroshus-matrix.jpg",
		// "55.74999","37.619977" , 1,
		// "Някрошюс, Някрошюс"));
		// points.add(new GmapPlace ("sceny-dj",
		// "/icons/sceny-dj-matrix170.jpg",
		// "55.74999","37.619977" , 1,
		// "Сцены из деревенской жизни, Погребничко"));
		// points.add(new GmapPlace ("skripka", "/icons/skripka-matrix.jpg",
		// "55.74999","37.619977" , 1,
		// "Скрипка Ротшильда, Кама Гинкас"));
		// points.add(new GmapPlace ("tri-sestry",
		// "/icons/tri-sestry-matrix.gif", "55.74999","37.619977" , 1,
		// "Три сестры, Юрий Любимов"));
		// currentId = "venus";
	}

	public void setCurrentId(Long currentId) {
		this.currentId = currentId;
	}

	public void setPoints(List<GmapPlace> points) {
		this.points = points;
	}
}
