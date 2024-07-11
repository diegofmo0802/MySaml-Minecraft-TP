import Matrix from "./Matrix.js";

class Plane  {
    /** @type {SVGElement} */
    plane;
    /**
     * that class draws points on the plane
     * @param {SVGElement} plane the plane where to draw the points
     */
    constructor(plane) {
        this.plane = plane;
        this.width = plane.clientWidth;
        this.height = plane.clientHeight;
        console.log(`new plane [${this.width}, ${this.height}]`)
    }
    clear() { this.plane.innerHTML = ""; }
    rotatePointX(x, y, z, cx, cy, cz, angle) {
        angle = angle * Math.PI / 180;
        const dx = x - cx;
        const dy = y - cy;
        const dz = z - cz;
        return [
            cx + dx,
            cy + (dy * Math.cos(angle) - dz * Math.sin(angle)),
            cz + (dy * Math.sin(angle) + dz * Math.cos(angle)),
        ];
    }
    rotatePointY(x, y, z, cx, cy, cz, angle) {
        angle = angle * Math.PI / 180;
        const dx = x - cx;
        const dy = y - cy;
        const dz = z - cz;
        return [
            cx + (dx * Math.cos(angle) - dz * Math.sin(angle)),
            cy + dy,
            cz + (dx * Math.sin(angle) + dz * Math.cos(angle))
        ];
    }
    rotatePointZ(x, y, z, cx, cy, cz, angle) {
        angle = angle * Math.PI / 180;
        const dx = x - cx;
        const dy = y - cy;
        const dz = z - cz;
        return [
            cx + (dx * Math.cos(angle) - dy * Math.sin(angle)),
            cy + (dx * Math.sin(angle) + dy * Math.cos(angle)),
            cz + dz,
        ];
    }
    rotatePoint(x, y, z, cx, cy, cz, angleX, angleY, angleZ) {
        angleX = angleX * Math.PI / 180;
        angleY = angleY * Math.PI / 180;
        angleZ = angleZ * Math.PI / 180;

        const cos = Math.cos;
        const sin = Math.sin;
    
        const rx = new Matrix(4, 4);
        const ry = new Matrix(4, 4);
        const rz = new Matrix(4, 4);
        const translation = new Matrix(4, 4);
    
        rx.setAll([
            [1, 0, 0, 0],
            [0, cos(angleX), -sin(angleX), 0],
            [0, sin(angleX), cos(angleX), 0],
            [0, 0, 0, 1]
        ]);
        ry.setAll([
            [cos(angleY), 0, sin(angleY), 0],
            [0, 1, 0, 0],
            [-sin(angleY), 0, cos(angleY), 0],
            [0, 0, 0, 1]
        ]);
        rz.setAll([
            [cos(angleZ), -sin(angleZ), 0, 0],
            [sin(angleZ), cos(angleZ), 0, 0],
            [0, 0, 1, 0],
            [0, 0, 0, 1]
        ]);
        translation.setAll([
            [1, 0, 0, -cx],
            [0, 1, 0, -cy],
            [0, 0, 1, -cz],
            [0, 0, 0, 1]
        ]);
        const reverseTranslation = new Matrix(4, 4);
        reverseTranslation.setAll([
            [1, 0, 0, cx],
            [0, 1, 0, cy],
            [0, 0, 1, cz],
            [0, 0, 0, 1]
        ]);
    
        const rotationMatrix = Matrix.Multiply(rz, Matrix.Multiply(ry, rx));
        const transformMatrix = Matrix.Multiply(reverseTranslation, Matrix.Multiply(rotationMatrix, translation));
    
        const point = new Matrix(4, 1);
        point.setAll([
            [x],
            [y],
            [z],
            [1]
        ]);
    
        const transformedPoint = Matrix.Multiply(transformMatrix, point);
    
        const newX = transformedPoint.get(0, 0);
        const newY = transformedPoint.get(1, 0);
        const newZ = transformedPoint.get(2, 0);
    
        // console.log(transformMatrix.toString());
        // console.log(point.toString());
        // console.log(transformedPoint.toString());
    
        return [newX, newY, newZ];
    }

    pointTo(x, y, color = "black") {
        x = x + (this.width / 2);
        y = y + (this.height / 2);
        let point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        point.setAttribute("cx", x);
        point.setAttribute("cy", y);
        point.setAttribute("r", 0.25);
        point.setAttribute("fill", color);
        this.plane.appendChild(point);
        // console.log(`new point on plane [${x}, ${y}]`);
    }
    circle(x, z, radius, density, duration, wait) {
        const quantity = Math.floor((2 * Math.PI * radius) / (density / 100)) * 5;
        const increment = (2 * Math.PI) / quantity;
        const sleep = Math.floor((duration * 1000) / quantity);
        const startDelay = wait * 1000;
        setTimeout(() => {
            for (let angle = 0; angle < (2 * Math.PI); angle += increment) {
                const summonX = x + Math.cos(angle) * radius;
                const summonZ = z + Math.sin(angle) * radius;
                this.pointTo(summonX, summonZ);
                setTimeout(() => {}, sleep);
            }
        }, startDelay);
    }
    square(x, z, size, ax, ay, az, density, duration, wait) {
        const quantity = parseInt((size / (density / 100)) * 5);
        const increment = (size) / quantity;
        const sleep = parseInt((duration * 1000) / quantity);
        const startDelay = parseInt(wait * 1000);
        const VectorR = [ax, ay, az];
        setTimeout(() => {
            for (let pos = 0; pos < size; pos += increment) {
                const initX = x - (size / 2) + pos;
                const initZ = z - (size / 2);
                const [summonX, summonY, summonZ] = this.rotatePoint(initX, 0, initZ, x, 0, z + (size /2)*0,...VectorR);
                this.pointTo(summonX, summonZ, "red");
                setTimeout(() => {}, sleep);
            }
            for (let pos = 0; pos < size; pos += increment) {
                const initX = x + (size / 2);
                const initZ = z - (size / 2) + pos;
                const [summonX, summonY, summonZ] = this.rotatePoint(initX, 0, initZ, x, 0, z + (size /2)*0,...VectorR);
                this.pointTo(summonX, summonZ, "green");
                setTimeout(() => {}, sleep);
            }
            for (let pos = 0; pos < size; pos += increment) {
                const initX = x + (size / 2) - pos;
                const initZ = z + (size / 2);
                const [summonX, summonY, summonZ] = this.rotatePoint(initX, 0, initZ, x, 0, z + (size /2)*0,...VectorR);
                this.pointTo(summonX, summonZ);
                setTimeout(() => {}, sleep);
            }
            for (let pos = 0; pos < size; pos += increment) {
                const initX = x - (size / 2);
                const initZ = z + (size / 2) - pos;
                const [summonX, summonY, summonZ] = this.rotatePoint(initX, 0, initZ, x, 0, z + (size /2)*0,...VectorR);
                this.pointTo(summonX, summonZ);
                setTimeout(() => {}, sleep);
            }
        }, startDelay);
    }
    polygon(x, z, radius, cx, cy, cz, sides, density) {
        let VectorR = [cx, cy, cz];
        let sideSize = 2 * radius * Math.sin(Math.PI / sides);
        let sideQuantity = parseInt((sideSize / (density / 100)) * 5);
        let NextItem = 0;

        for (let side = 0; side < sides; side++) {
            let initAngle = 2 * Math.PI / sides * side;
            let endAngle = 2 * Math.PI / sides * (side +1);
            let startX = x + Math.cos(initAngle) * radius;
            let startZ = z + Math.sin(initAngle) * radius;
            let endX = x + Math.cos(endAngle) * radius;
            let endZ = z + Math.sin(endAngle) * radius;
            for (let pointIndex = 0; pointIndex < sideQuantity; pointIndex++) {
                let PointIncrement = pointIndex / sideQuantity;

                let initX = startX + PointIncrement * (endX - startX);
                let initZ = startZ + PointIncrement * (endZ - startZ);
                const [summonX, summonY, summonZ] = this.rotatePoint(initX, 0, initZ, x, 0, z,...VectorR);
                this.pointTo(summonX, summonZ);
                NextItem++;
            }
        }
    }
}
export default Plane;