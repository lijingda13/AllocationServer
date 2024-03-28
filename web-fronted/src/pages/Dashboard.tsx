import { Card, CardContent, CardHeader } from "@mui/material";
import "../share/styles.css"
import Typography from '@mui/material/Typography';
import { useEffect, useState } from "react";
export const Dashboard = () => (
    <Card className="dashboard-content" sx={{backgroundColor:"rgb(249,249,225)"}}>
        <CardHeader className="dashboard-title" sx={{fontSize: "40px"}} title="Project Allocation System" />
        <CardContent>
            <div className="dashboard-tip">Welcome to the project allocation system!</div>
            <BasicCard/>
        </CardContent>
    </Card>
);

const BasicCard = () => {
    const [time, setTime] = useState('');
    const getTime = () => {
        const date = new Date();
        let h:any = date.getHours();
        h = h < 10 ? '0' + h : h;
        let d:any = date.getMinutes();
        d = d < 10 ? '0' + d : d;

        let s:any = date.getSeconds();
        s = s < 10 ? '0' + s : s;

        setTime(h + ':' + d + ':' + s);
    }
    useEffect(() => {
        const intervalId = setInterval(() => {
          const date = new Date();
          let h: any = date.getHours();
          h = h < 10 ? '0' + h : h;
          let d: any = date.getMinutes();
          d = d < 10 ? '0' + d : d;
    
          let s: any = date.getSeconds();
          s = s < 10 ? '0' + s : s;
    
          setTime(h + ':' + d + ':' + s);
        }, 1000);
    
        return () => clearInterval(intervalId); 
      }, []); 

    const getDate = () => {
        const date = new Date();
        let month = date.getMonth();
        let dater = date.getDate();
        let day = date.getDay();
        let arr = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        return {d: dater + " / " + (month + 1) + " / " + 2024, w: arr[day] };
    }
    
    const result:any = getDate();
    const dates = result.d
    const week = result.w
  return (
    <Card sx={{ minWidth: 275 }}>
      <CardContent className="cal-content">
        <Typography className="cal-today" sx={{ fontSize: 20 }} color="text.secondary" gutterBottom>
          Today
        </Typography>
        <Typography sx={{fontSize:"30px"}} variant="h5" component="div" color="text.secondary">
          {week}
        </Typography>
        <Typography sx={{fontSize:"30px"}} color="text.secondary">
          {dates}
        </Typography>
        <Typography sx={{fontSize:"30px"}} variant="body2" color="text.secondary">
          {time}
        </Typography>
      </CardContent>
    </Card>
  );
}