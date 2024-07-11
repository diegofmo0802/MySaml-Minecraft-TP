import Plane from "./plane.js";

const tests = {
    plane: function() {
        let planeElement = document.getElementById("plane");
        let plane = new Plane(planeElement);
        //plane.square(0, 0, 50, 0, 0, 0, 100, 0, 0);
        plane.circle(0, 0, 25, 100, 1, 0);
        plane.polygon(0, 0, 25, 0, 0, 0, 4, 100);
        plane.polygon(0, 0, 25, 0, 45, 0, 4, 100);
        
        plane.circle(0, 0, 17.5, 100, 1, 0);
        plane.polygon(0, 0, 17.5, 0, 0, 0, 3, 100);
        plane.polygon(0, 0, 17.5, 0, 90, 0, 3, 100);
        plane.polygon(0, 0, 17.5, 0, 180, 0, 3, 100);
        plane.polygon(0, 0, 17.5, 0, 270, 0, 3, 100);
        
        plane.circle(0, -25, 5, 100, 1, 0);
        plane.circle(0, 25, 5, 100, 1, 0);
        plane.circle(-25, 0, 5, 100, 1, 0);
        plane.circle(25, 0, 5, 100, 1, 0);

        plane.polygon(0, 0, 8, 0, 45, 0, 12, 100);
    }
}
export default tests;