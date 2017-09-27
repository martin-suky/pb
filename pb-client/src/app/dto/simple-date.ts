export class SimpleDate {
    constructor(public readonly year: number, public readonly month: number) {}

    public increment(): SimpleDate {
        let newMonth = this.month + 1;
        let newYear = this.year;
        if (newMonth === 13) {
            newMonth = 1;
            newYear ++;
        }
        return new SimpleDate(newYear, newMonth);
    }

    public compareTo(other: SimpleDate): number {
        return this.comparePrimitiveTo(other.year, other.month);
    }

    public comparePrimitiveTo(year: number, month: number): number {
        if (this.year === year && this.month === month) {
            return 0;
        } else if (this.year < year || (this.year === year && this.month < month)) {
            return -1;
        } else {
            return 1;
        }
    }

    public equals(other: SimpleDate) {
      return this.equalsToPrimitive(other.year, other.month);
    }

    public equalsToPrimitive(year: number, month: number): boolean {
        return this.year === year && this.month === month;
    }

    public toString(): string {
        return `${this.year}-${this.month}`;
    }

    public static now(): SimpleDate {
        const date = new Date();
        return new SimpleDate(date.getFullYear(), date.getMonth() + 1);
    }
}
